package com.alexkasko.robots.service;

import com.alexkasko.robots.dao.MessageLogDao;
import com.alexkasko.robots.dao.MessageLogDaoImpl;
import com.alexkasko.robots.entity.MessageLog;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class MessageLogServiceTest {

    @Resource
    private MessageLogService messageLogService;

    @Before
    public void setUp() throws Exception {
         messageLogService = new MessageLogServiceImpl(new MessageLogDaoImpl());
    }

    @Test
    public void testMessageLogService() {
        // В начале список должен быть пуст
        assertTrue(messageLogService.findAll().isEmpty());
        String testString = "test string";
        // Добавляем запись в лог
        messageLogService.save(testString);
        List<MessageLog> logs = messageLogService.findAll();
        // Должна быть одна запись в логе
        assertEquals(1, logs.size());
        // И поле message должно соответствовать тестовой строке
        assertEquals(testString, logs.get(0).getMessage());
    }
}