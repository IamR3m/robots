package com.alexkasko.robots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
public class ActivityTrackerRunner implements Runnable {

    private final ActivityTracker activityTracker;

    public ActivityTrackerRunner(ActivityTracker activityTracker) {
        this.activityTracker = activityTracker;
        this.activityTracker.prepareQueue();
        this.activityTracker.prepareRobots();
    }

    @Override
    public void run() {
        activityTracker.setIsRunning(true);
        while (activityTracker.checkQueue()) {
            activityTracker.checkRobotsExist();
            try {
                activityTracker.runNextTask();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        activityTracker.setIsRunning(false);
    }

}
