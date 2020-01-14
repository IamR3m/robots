package com.alexkasko.robots.service;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;
import com.alexkasko.robots.entity.enums.ERobotType;

import java.util.List;

public interface ActivityTracker {

    // создать очередь задач
    void prepareQueue();

    // создать роботов
    void prepareRobots();

    // создать одного робота
    void createRobot();

    // создать робота определенного типа
    void createRobot(ERobotType robotType);

    // проверить наличие роботов
    void checkRobotsExist();

    // проверить очередь
    boolean checkQueue();

    // послать задачу из очереди первому свободному подходящему роботу
    void runNextTask() throws InterruptedException;

    // добавить задачу
    boolean addTask(Task task);

    // получить список логов
    List<MessageLog> getMessageLog();

    boolean getIsRunning();

    void setIsRunning(boolean isRunning);
}
