package com.alexkasko.robots.dao;

import com.alexkasko.robots.entity.MessageLog;

import java.util.List;

public interface MessageLogDao {

    void save(String message);

    List<MessageLog> findAll();
}
