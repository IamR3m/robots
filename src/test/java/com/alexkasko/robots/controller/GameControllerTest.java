package com.alexkasko.robots.controller;

import com.alexkasko.robots.config.ThreadConfig;
import com.alexkasko.robots.config.WebAppInitializer;
import com.alexkasko.robots.config.WebConfig;
import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;
import com.alexkasko.robots.service.ActivityTracker;
import com.alexkasko.robots.service.ActivityTrackerRunner;
import com.alexkasko.robots.service.MessageLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class GameControllerTest {

    @Autowired
    ActivityTracker activityTracker;

    @Autowired
    ActivityTrackerRunner activityTrackerRunner;

    @Autowired
    MessageLogService messageLogService;

    @Autowired
    ThreadConfig threadConfig;

    private GameController gameController;

    @Before
    public void setUp() throws Exception {
        this.gameController = new GameController(activityTracker,
                activityTrackerRunner,
                messageLogService,
                threadConfig.threadPoolTaskExecutor());
    }

    @Test
    public void readLog() {
        List<MessageLog> messageLogs = gameController.readLog();
        assertFalse(messageLogs.isEmpty());
    }

    @Test
    public void addTask() {
        ResponseEntity<String> responseEntity = gameController.addTask(new Task());
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals("ok", responseEntity.getBody());
    }
}