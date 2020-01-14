package com.alexkasko.robots.service;

import com.alexkasko.robots.dao.MessageLogDao;
import com.alexkasko.robots.dao.MessageLogDaoImpl;
import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Robot;
import com.alexkasko.robots.entity.Task;
import com.alexkasko.robots.entity.enums.ERobotType;
import com.alexkasko.robots.entity.enums.ETaskType;
import com.alexkasko.robots.entity.robots.*;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityTrackerTest {

    @Resource
    private ActivityTracker activityTracker;

    @Resource
    private MessageLogDao messageLogDao = new MessageLogDaoImpl();

    @Resource
    private MessageLogService messageLogService = new MessageLogServiceImpl(messageLogDao);

    @Before
    public void setUp() throws Exception {
        activityTracker = new ActivityTrackerImpl(messageLogService);
    }

    // Проверка создания очереди. Очередь не должна быть пуста
    @Test
    public void prepareQueue() {
        activityTracker.prepareQueue();
        assertNotNull(activityTracker.getQueue());
    }

    // Проверка создания роботов. Должны быть созданы роботы 4-х типов
    @Test
    public void prepareRobots() {
        List<Robot> robots = new ArrayList<>();
        robots.add(new Autobot());
        robots.add(new NS5());
        robots.add(new R2D2());
        robots.add(new T1000());
        activityTracker.prepareRobots();
        assertEquals(robots, activityTracker.getRobots());
    }

    // Проверка создания случайного робота
    @Test
    public void createRobotDefault() {
        activityTracker.createRobot();
        assertEquals(1,activityTracker.getRobots().size());
    }

    // Проверка создания робота определенного типа
    @Test
    public void createRobot() {
        activityTracker.createRobot(ERobotType.AUTOBOT);
        activityTracker.createRobot(ERobotType.NS5);
        activityTracker.createRobot(ERobotType.R2D2);
        activityTracker.createRobot(ERobotType.T1000);
        List<Robot> robots = activityTracker.getRobots();
        assertTrue(robots.get(0).getName().contains("Autobot"));
        assertTrue(robots.get(1).getName().contains("NS5"));
        assertTrue(robots.get(2).getName().contains("R2D2"));
        assertTrue(robots.get(3).getName().contains("T1000"));
    }

    // Робот типа COMMON не может быть создан
    @Test(expected = RuntimeException.class)
    public void createRobotFail() {
        activityTracker.createRobot(ERobotType.COMMON);
    }

    @Test
    public void checkRobots() {
        // Создаем роботов
        activityTracker.prepareRobots();
        // Добавляем задачу для R2D2 убить себя
        activityTracker.addTask(new Task(ERobotType.R2D2,0,ETaskType.SELF_DESTROY));
        // Отправляем задачу
        try {
            activityTracker.runNextTask();
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
        // Выполняем проверку роботов
        activityTracker.checkRobots();
        // Получаем роботов после проверки
        List<Robot> robots = activityTracker.getRobots();
        // На 3 позиции не должен быть R2D2, он убил себя и checkRobots() должен был его удалить
        assertNotEquals("R2D2", robots.get(2).getName());
        // На этой позиции должен стоять следующий, T1000
        assertEquals("T1000", robots.get(2).getName());
        // Так же checkRobots() должен был добавить нового R2D2 взамен убитого
        assertTrue(robots.get(3).getName().contains("R2D2"));
    }

    // Проверка метода проверки очереди
    @Test
    public void checkQueue() {
        // Пустая очередь должна вернуть False
        assertFalse(activityTracker.checkQueue());
        // Заполненная очередь должна вернуть True
        activityTracker.prepareQueue();
        assertTrue(activityTracker.checkQueue());
    }

    @Test
    public void runNextTask() {
        //Подготавливаем очередь и роботов
        activityTracker.prepareQueue();
        activityTracker.prepareRobots();
        // Т.к. очередь создается случайного размера, сохраняем исходный размер
        int queueSize = activityTracker.getQueue().size();
        try {
            activityTracker.runNextTask();
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
        // После запуска задания очередь должна уменьшиться на 1
        assertEquals(queueSize - 1, activityTracker.getQueue().size());

    }

    // Проверка добавления задачи
    @Test
    public void addTask() {
        // Успешное добавление задачи возвращает True
        assertTrue(activityTracker.addTask(new Task()));
        // И размер очереди будет 1
        assertEquals(1,activityTracker.getQueue().size());
    }

    // Проверка получения лога
    @Test
    public void getMessageLog() {
        activityTracker.prepareQueue();
        List<MessageLog> messageLog = activityTracker.getMessageLog();
        assertEquals(1,messageLog.size());
        assertTrue(messageLog.get(0).getMessage().contains("tasks created and added to queue"));
    }

    @Test
    public void getIsRunning() {
        assertFalse(activityTracker.getIsRunning());
        activityTracker.setIsRunning(true);
        assertTrue(activityTracker.getIsRunning());
    }
}