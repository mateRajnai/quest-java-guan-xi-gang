package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.util.Direction;

public class Skeleton extends IntelligentFoe implements Vulnerable {
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public void approach(Direction vector) {

    }

    @Override
    public void patrol() {

    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void terminate() {
        this.cell.setMapEntity(null);
        this.setCell(null);
    }
}
