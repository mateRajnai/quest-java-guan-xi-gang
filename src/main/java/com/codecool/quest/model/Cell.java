package com.codecool.quest.model;

public class Cell implements Drawable {
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

    public Actor getActor() {
        return actor;
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }
}
