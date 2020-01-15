package com.alexkasko.robots.entity;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class MessageLog {
    private static final AtomicLong count = new AtomicLong(0);
    private Long id;
    private LocalDateTime time;
    private String message;

    public MessageLog() {
    }

    public MessageLog(Long id, LocalDateTime time, String message) {
        this.id = id;
        this.time = time;
        this.message = message;
    }

    public MessageLog(String message) {
        this.id = count.incrementAndGet();
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageLog{" + "id=" + id + ", time='" + time + "', message='" + message + "'}";
    }
}
