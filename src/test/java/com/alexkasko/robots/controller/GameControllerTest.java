package com.alexkasko.robots.controller;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/*
* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
* !!!!!!!!!!!!!    WARNING    !!!!!!!!!!!!!!
* Test don't work. You shouldn't run it!!!!!
* */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GameControllerTest {

    @Autowired
    GameController gameController;

    @Before
    public void setUp() throws Exception {
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