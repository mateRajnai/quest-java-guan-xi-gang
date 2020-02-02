package com.codecool.quest.logic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Direction {
    private final int dx;
    private final int dy;

    public static final Direction UP = new Direction(0, -1);
    public static final Direction DOWN = new Direction(0, 1);
    public static final Direction RIGHT = new Direction(1, 0);
    public static final Direction LEFT = new Direction(-1, 0);
    public static final List<Direction> MAIN_DIRECTIONS = new ArrayList<>(Arrays.asList(UP, RIGHT, DOWN, LEFT));

    public Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction xFlipped() {
        return new Direction(-dx, dy);
    }

    public Direction yFlipped() {
        return new Direction(dx, -dy);
    }

    public Direction reversed() {
        return new Direction(-dx, -dy);
    }

    public Direction yComponent() {
        return new Direction(0, dy);
    }

    public Direction xComponent() {
        return new Direction(dx, 0);
    }
}
