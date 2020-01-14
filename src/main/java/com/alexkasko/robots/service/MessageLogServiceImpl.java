package com.alexkasko.robots.service;

import com.alexkasko.robots.dao.MessageLogDao;
import com.alexkasko.robots.entity.MessageLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageLogServiceImpl implements MessageLogService {

    @Autowired
    public MessageLogDao messageLogDao;

    @Override
    public List<MessageLog> findAll() {
        return messageLogDao.findAll();
    }

    @Override
    public void save(String message) {
        messageLogDao.save(message);
    }
}
