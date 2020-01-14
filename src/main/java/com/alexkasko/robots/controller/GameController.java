package com.alexkasko.robots.controller;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;
import com.alexkasko.robots.service.ActivityTracker;
import com.alexkasko.robots.service.ActivityTrackerRunner;
import com.alexkasko.robots.service.MessageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private final ActivityTracker activityTracker;
    private final ActivityTrackerRunner activityTrackerRunner;
    private final MessageLogService messageLogService;
    private final TaskExecutor taskExecutor;

    @Autowired
    public GameController(ActivityTracker activityTracker,
                          ActivityTrackerRunner activityTrackerRunner,
                          MessageLogService messageLogService,
                          TaskExecutor threadPoolTaskExecutor) {
        this.activityTrackerRunner = activityTrackerRunner;
        this.messageLogService = messageLogService;
        this.activityTracker = activityTracker;
        this.taskExecutor = threadPoolTaskExecutor;
    }

    @GetMapping("/log")
    public @ResponseBody List<MessageLog> readLog() {
        return messageLogService.findAll();
    }

    @PostMapping("/task")
    public @ResponseBody ResponseEntity<String> addTask(@Valid @RequestBody Task task) {
        if(activityTracker.addTask(task)) {
            if (!activityTracker.getIsRunning())
                taskExecutor.execute(activityTrackerRunner);
            return new ResponseEntity<String>("ok", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
    }
}
