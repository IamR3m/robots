package com.alexkasko.robots.entity;

import com.alexkasko.robots.entity.enums.ERobotState;
import com.alexkasko.robots.entity.enums.ERobotType;
import com.alexkasko.robots.entity.enums.ETaskType;
import com.alexkasko.robots.service.MessageLogService;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.TimeUnit;

public abstract class Robot {
    private final String name;
    private final int speed;
    private ERobotState state;

    public Robot(String name, int speed) {
        this.name = name;
        this.speed = speed;
        this.state = ERobotState.IDLE;
    }

    public String getName() {
        return name;
    }

    public abstract ERobotType getType();

    public int getSpeed() {
        return speed;
    }

    public ERobotState getState() {
        return state;
    }

    public void setState(ERobotState state) {
        this.state = state;
    }

    @Async
    public void doTask(Task task, MessageLogService messageLogService) throws InterruptedException {
        if (task.getTaskType() == ETaskType.SELF_DESTROY) {
            this.state = ERobotState.DESTROYED;
            messageLogService.save("** " + this.name + " " + ERobotState.DESTROYED.getTitle() + "! **");
            return;
        }
        this.state = ERobotState.WORKING;
        messageLogService.save("-- " + this.name + " start task --");
        int remaining = task.getEstimate();
        while (remaining > 0) {
            remaining -= this.speed;
            TimeUnit.SECONDS.sleep(1);
        }
        this.state = ERobotState.IDLE;
        messageLogService.save("-- " + this.name + " end task --");
    }
}
