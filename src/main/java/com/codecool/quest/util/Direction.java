package com.codecool.quest.util;

public enum Direction {
    UP(0, -1),
    RIGHT_UP(1, -1),
    RIGHT(1, 0),
    RIGHT_DOWN(1, 1),
    DOWN(0, 1),
    LEFT_DOWN(-1, 1),
    LEFT(-1, 0),
    LEFT_UP(-1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction getDirection(int dx, int dy) {
        for (Direction direction : values())
            if (direction.dx == dx && direction.dy == dy)
                return direction;
        return null;
    }

    public Direction fullFlipped() {
        return getDirection(this.dx * (-1), this.dy * (-1));
    }

    public Direction xFlipped() {
        return getDirection(this.dx * (-1), this.dy);
    }

    public Direction yFlipped() {
        return getDirection(this.dx, this.dy * (-1));
    }

    public Direction yComponent() {
        if (this.dy > 0)
            return DOWN;
        else
            return UP;
    }

    public Direction xComponent() {
        if (this.dx > 0)
            return RIGHT;
        else
            return LEFT;
    }
}
