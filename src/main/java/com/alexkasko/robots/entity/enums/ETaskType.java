package com.alexkasko.robots.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ETaskType {
    @JsonProperty("Work")
    WORK("Work"),
    @JsonProperty("Self Destroy")
    SELF_DESTROY("Self Destroy");

    private final String title;
    private static final List<ETaskType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();

    ETaskType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ETaskType randomType() {
        return VALUES.get(ThreadLocalRandom.current().nextInt(10) > 2 ? 0 : 1);
    }

    @Override
    public String toString() {
        return "ETaskType{title='" + this.title + "'}";
    }
}
