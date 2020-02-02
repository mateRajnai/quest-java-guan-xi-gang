package com.codecool.quest.logic.util;

public enum CardinalDirection {
    UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

    private final int dx;
    private final int dy;

    CardinalDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction get(CardinalDirection cardinalDirection) {
        return new Direction(dx, dy);
    }
}
