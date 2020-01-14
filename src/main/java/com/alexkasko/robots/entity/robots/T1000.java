package com.alexkasko.robots.entity.robots;

import com.alexkasko.robots.entity.Robot;
import com.alexkasko.robots.entity.enums.ERobotType;

public class T1000 extends Robot {
    public T1000() {
        super("T1000", 10);
    }

    public T1000(String name) {
        super(name, 10);
    }

    @Override
    public ERobotType getType() {
        return ERobotType.T1000;
    }
}
