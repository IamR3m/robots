package com.alexkasko.robots.entity.robots;

import com.alexkasko.robots.entity.Robot;
import com.alexkasko.robots.entity.enums.ERobotType;

public class R2D2 extends Robot {
    public R2D2() {
        super("R2D2", 5);
    }

    public R2D2(String name) {
        super(name, 5);
    }

    @Override
    public ERobotType getType() {
        return ERobotType.R2D2;
    }
}
