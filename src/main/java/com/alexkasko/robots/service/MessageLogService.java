package com.alexkasko.robots.service;

import com.alexkasko.robots.entity.MessageLog;

import java.util.List;

public interface MessageLogService {

    List<MessageLog> findAll();

    void save(String message);
}
