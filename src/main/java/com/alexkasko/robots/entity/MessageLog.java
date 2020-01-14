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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.message == null) ? 0 : this.message.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.time == null) ? 0 : this.time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MessageLog other = (MessageLog) obj;
        if (message == null) {
            if (other.message != null) return false;
        } else if (!message.equals(other.message)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (time == null) {
            if (other.time != null) return false;
        } else if (!time.equals(other.time)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", time='" + time + "', message='" + message + "'}";
    }
}
