package com.alexkasko.robots.entity.robots;

import com.alexkasko.robots.entity.Robot;
import com.alexkasko.robots.entity.enums.ERobotType;

public class Autobot extends Robot {
    public Autobot() {
        super("Optimus Prime", 25);
    }

    public Autobot(String name) {
        super(name, 25);
    }

    @Override
    public ERobotType getType() {
        return ERobotType.AUTOBOT;
    }
}
