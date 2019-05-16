package com.codecool.quest.model;

public class Cell {
    private CellType type = CellType.EMPTY;
    private Actor actor;

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
