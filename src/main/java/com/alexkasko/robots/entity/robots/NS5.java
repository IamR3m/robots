package com.alexkasko.robots.entity.robots;

import com.alexkasko.robots.entity.Robot;
import com.alexkasko.robots.entity.enums.ERobotType;

public class NS5 extends Robot {
    public NS5() {
        super("Sonny", 20);
    }

    public NS5(String name) {
        super(name, 20);
    }

    @Override
    public ERobotType getType() {
        return ERobotType.NS5;
    }
}
