package com.alexkasko.robots.dao;

import com.alexkasko.robots.entity.MessageLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageLogDaoImpl implements MessageLogDao {

    private List<MessageLog> messageLogs = new ArrayList<>();

    @Override
    public void save(String message) {
        messageLogs.add(new MessageLog(message));
    }

    @Override
    public List<MessageLog> findAll() {
        return messageLogs;
    }
}
