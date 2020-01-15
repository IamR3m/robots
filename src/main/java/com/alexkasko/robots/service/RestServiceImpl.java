package com.alexkasko.robots.service;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestServiceImpl implements RestService {

    private final ActivityTracker activityTracker;
    private final ActivityTrackerRunner activityTrackerRunner;
    private final MessageLogService messageLogService;
    private final TaskExecutor taskExecutor;

    @Autowired
    public RestServiceImpl(ActivityTracker activityTracker,
                           ActivityTrackerRunner activityTrackerRunner,
                           MessageLogService messageLogService,
                           @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.activityTracker = activityTracker;
        this.activityTrackerRunner = activityTrackerRunner;
        this.messageLogService = messageLogService;
        this.taskExecutor = taskExecutor;
    }


    @Override
    public List<MessageLog> readLog() {
        return messageLogService.findAll();
    }

    @Override
    public boolean addTask(Task task) {
        if(activityTracker.addTask(task)) {
            if (!activityTracker.getIsRunning()) {
                taskExecutor.execute(activityTrackerRunner);
            }
            return true;
        }
        return false;
    }
}
