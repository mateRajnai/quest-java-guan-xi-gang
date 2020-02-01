package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Foe {
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
