package com.alexkasko.robots.service;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
public class ActivityTrackerRunner implements Runnable {

    private Logger logger;
    private final ActivityTracker activityTracker;
    private final int SLEEP_TIME = 5;

    public ActivityTrackerRunner(ActivityTracker activityTracker) {
        this.activityTracker = activityTracker;
        this.activityTracker.prepareQueue();
        this.activityTracker.prepareRobots();
    }

    @Override
    public void run() {
        activityTracker.setIsRunning(true);
        while (activityTracker.checkQueue()) {
            activityTracker.checkRobots();
            try {
                activityTracker.runNextTask();
                TimeUnit.SECONDS.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("Interrupted while sleeping", e);
            }
        }
        activityTracker.setIsRunning(false);
    }

}
