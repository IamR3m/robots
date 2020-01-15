package com.alexkasko.robots.service;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Robot;
import com.alexkasko.robots.entity.Task;
import com.alexkasko.robots.entity.enums.ERobotState;
import com.alexkasko.robots.entity.enums.ERobotType;
import com.alexkasko.robots.entity.enums.ETaskType;
import com.alexkasko.robots.entity.robots.Autobot;
import com.alexkasko.robots.entity.robots.NS5;
import com.alexkasko.robots.entity.robots.R2D2;
import com.alexkasko.robots.entity.robots.T1000;
import com.alexkasko.robots.random.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component("activityTracker")
@EnableAsync
public class ActivityTrackerImpl implements ActivityTracker {

    private final int TASK_COUNT_MIN = 10;
    private final int TASK_COUNT_MAX = 50;
    private final int ESTIMATE_MIN = 50;
    private final int ESTIMATE_MAX = 500;

    private ArrayDeque<Task> queue = new ArrayDeque<>();
    private List<Robot> robots = new ArrayList<>();
    private boolean isRunning = false;

    private final MessageLogService messageLogService;

    @Autowired
    public ActivityTrackerImpl(MessageLogService messageLogService) {
        this.messageLogService = messageLogService;
    }

    @Override
    public void prepareQueue() {
        // Создание случайного количества (от 10 до 50) случайных задач.
        int taskCount = ThreadLocalRandom.current().nextInt(TASK_COUNT_MIN, TASK_COUNT_MAX);
        for (int i = 0; i < taskCount; i++) {
            // Конструктор задачи по умолчанию создает случайную задачу
            this.queue.add(new Task(ERobotType.randomType(),
                    ThreadLocalRandom.current().nextInt(ESTIMATE_MIN, ESTIMATE_MAX),
                    ETaskType.randomType()));
        }
        messageLogService.save("Activity Tracker: " + taskCount + " tasks created and added to queue");
    }

    @Override
    public void prepareRobots() {
        // Добавление роботов по одному каждого типа
        robots.add(new Autobot());
        robots.add(new NS5());
        robots.add(new R2D2());
        robots.add(new T1000());

        messageLogService.save("Activity Tracker: Robots prepared");
    }

    @Override
    public void createRobot() {
        createRobot(ERobotType.randomType());
    }

    @Override
    // Добавление робота заданного типа
    public void createRobot(ERobotType robotType) {
        String robotName;
        // Генерация случайного имени робота с заданным префиксом и создание робота заданного типа
        switch (robotType) {
            case AUTOBOT:
                robotName = "Autobot " + RandomString.randomAlphanumericString();
                robots.add(new Autobot(robotName));
                break;
            case NS5:
                robotName = "NS5 " + RandomString.randomAlphanumericString();
                robots.add(new NS5(robotName));
                break;
            case R2D2:
                robotName = "R2D2 " + RandomString.randomAlphanumericString();
                robots.add(new R2D2(robotName));
                break;
            case T1000:
                robotName = "T1000 " + RandomString.randomAlphanumericString();
                robots.add(new T1000(robotName));
                break;
            default:
                throw new RuntimeException("Wrong robot type");
        }
        messageLogService.save("Activity Tracker: robot '" + robotName + "' created");
    }

    @Override
    public void checkRobots() {
        List<Robot> destroyedRobots = new ArrayList<>();

        // Проверка наличия свободных роботов каждого типа
        for (ERobotType robotType : ERobotType.values()) {
            // Робота типа COMMON создать нельзя
            if(robotType == ERobotType.COMMON) continue;
            boolean exist = false;

            for (Robot robot : robots) {
                // Если находим убитого робота - пополняем список убитых роботов
                if (robot.getState() == ERobotState.DESTROYED) {
                    destroyedRobots.add(robot);
                }
                // Если робот соответствующего типа имеет статус IDLE, то искомый робот найден
                if (robot.getType().equals(robotType) && robot.getState().equals(ERobotState.IDLE)) {
                    exist = true;
                    break;
                }
            }

            // Создаем новых роботов взамен убитых
            for (Robot destroyedRobot : destroyedRobots) {
                createRobot(destroyedRobot.getType());
            }
            // Удаляем убитых роботов
            robots.removeAll(destroyedRobots);
            destroyedRobots.clear();

            // Если свободного робота определенного типа нет, то создадим нового
            if (!exist) createRobot(robotType);
        }
    }

    @Override
    public boolean checkQueue() {
        return !queue.isEmpty();
    }

    @Override
    @Async
    public void runNextTask() throws InterruptedException {
        // Проверяем очередь на наличие задач
        if (queue.peek() != null) {
            // Извлекаем задачу из очереди
            Task task = queue.pop();
            // Ищем подходящего робота
            for (Robot robot : robots) {
                // Если робот свободен и тип робота задачи - COMMON или совпадает с типом робота
                if (robot.getState().equals(ERobotState.IDLE) && (task.getRobotType().equals(ERobotType.COMMON) ||
                        robot.getType().equals(task.getRobotType()))) {
                    // Посылаем роботу задачу и обнуляем задачу
                    robot.doTask(task, messageLogService);
                    task = null;
                    break;
                }
            }
            /*
            * Если задача не пуста (не найден свободный робот) то возвращаем задачу в начало очереди.
            * В следующей итерации в ActivityTrackerRunner.cycleRun() будет добавлен необходимый робот
            * */
            if (task != null) {
                queue.addFirst(task);
            }
        }
    }

    @Override
    public boolean addTask(Task task) {
        queue.add(task);
        messageLogService.save("Activity Tracker: Task has been added");
        return true;
    }

    @Override
    public List<MessageLog> getMessageLog() {
        return messageLogService.findAll();
    }

    @Override
    public boolean getIsRunning() {
        return this.isRunning;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public List<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public ArrayDeque<Task> getQueue() {
        return this.queue;
    }
}
