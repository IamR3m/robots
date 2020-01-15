package com.alexkasko.robots.entity.enums;

public enum ERobotState {
    IDLE("Idle"),
    WORKING("Working"),
    DESTROYED("Destroyed");

    private final String title;

    ERobotState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ERobotState{title='" + this.title + "'}";
    }
}
