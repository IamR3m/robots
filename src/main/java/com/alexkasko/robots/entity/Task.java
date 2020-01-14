package com.alexkasko.robots.entity;

import com.alexkasko.robots.entity.enums.ERobotType;
import com.alexkasko.robots.entity.enums.ETaskType;

import java.util.concurrent.ThreadLocalRandom;

public class Task {

    private final int ESTIMATE_MIN = 50;
    private final int ESTIMATE_MAX = 500;

    private ERobotType robotType;
    private int estimate;
    private ETaskType taskType;

    public Task() {
        this.robotType = ERobotType.randomType();
        this.estimate = ThreadLocalRandom.current().nextInt(ESTIMATE_MIN,ESTIMATE_MAX);
        this.taskType = ETaskType.randomType();
    }

    public Task(ERobotType robotType, int estimate, ETaskType taskType) {
        this.robotType = robotType;
        this.estimate = estimate;
        this.taskType = taskType;
    }

    public Task(String robotType, int estimate, String taskType) {
        this.robotType = ERobotType.valueOf(robotType);
        this.estimate = estimate;
        this.taskType = ETaskType.valueOf(taskType);
    }

    public ERobotType getRobotType() {
        return robotType;
    }

    public int getEstimate() {
        return estimate;
    }

    public ETaskType getTaskType() {
        return taskType;
    }

    public void setRobotType(ERobotType robotType) {
        this.robotType = robotType;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    public void setTaskType(ETaskType taskType) {
        this.taskType = taskType;
    }
}
