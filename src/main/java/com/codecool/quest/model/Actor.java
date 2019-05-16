package com.codecool.quest.model;

public abstract class Actor {
    private Cell cell;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    abstract String getTileName();
}
