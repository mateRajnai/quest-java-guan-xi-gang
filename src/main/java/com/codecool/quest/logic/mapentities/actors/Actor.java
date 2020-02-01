package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.mapentities.MapEntity;

public abstract class Actor extends MapEntity implements Drawable {

    public Actor(Cell cell) {
        super(cell);
    }

    public void moveTo(Cell cell) {
        this.cell.setMapEntity(null);
        cell.setMapEntity(this);
        this.setCell(cell);
    }
}
