package com.alexkasko.robots.service;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;

import java.util.List;

public interface RestService {

        List<MessageLog> readLog();

        boolean addTask(Task task);

}
