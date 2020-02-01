package com.codecool.quest.logic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {
    private int dx;
    private int dy;

    public static final Vector UP = new Vector(0, -1);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector RIGHT = new Vector(1, 0);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final List<Vector> MAIN_VECTORS = new ArrayList<>(Arrays.asList(UP, RIGHT, DOWN, LEFT));

    public Vector(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void xFlip() {
        dx *= -1;
    }

    public void yFlip() {
        dy *= -1;
    }

    public void reverse() {
        xFlip();
        yFlip();
    }

    public Vector xFlipped() {
        return new Vector(-dx, dy);
    }

    public Vector yFlipped() {
        return new Vector(dx, -dy);
    }

    public Vector reversed() {
        return new Vector(-dx, -dy);
    }

    public Vector yComponent() {
        return new Vector(0, dy);
    }

    public Vector xComponent() {
        return new Vector(dx, 0);
    }
}
