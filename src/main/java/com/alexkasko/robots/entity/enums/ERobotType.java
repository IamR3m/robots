package com.alexkasko.robots.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ERobotType {
    @JsonProperty("Autobot")
    AUTOBOT("Autobot"),
    @JsonProperty("R2D2")
    R2D2("R2D2"),
    @JsonProperty("NS5")
    NS5("NS5"),
    @JsonProperty("T1000")
    T1000("T1000"),
    @JsonProperty("Common")
    COMMON("Common");

    private String title;
    private static final List<ERobotType> VALUES = Collections.unmodifiableList(Arrays.asList(AUTOBOT,R2D2,NS5,T1000));
    private static final int SIZE = VALUES.size();

    ERobotType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ERobotType randomType() {
        return VALUES.get(new Random().nextInt(SIZE));
    }

    @Override
    public String toString() {
        return "ERobotType{title='" + this.title + "'}";
    }
}
