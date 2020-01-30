package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Actor {

    private static final int INITIAL_HEALTH = 1;
    private String whoAmI = "chest closed";

    public Chest(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
    }

    @Override
    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.CHEST_OPEN);
        whoAmI = "open chest";
    }

    @Override
    public boolean hasFixedPosition() {
        return true;
    }

    @Override
    public String getTileName() {
        return whoAmI;
    }
}
