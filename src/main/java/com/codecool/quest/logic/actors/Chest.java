package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chest extends Actor {

    private static final int INITIAL_HEALTH = 1;
    private String whoAmI = "chest closed";
    private static List<Chest> chests = new ArrayList<>();

    public Chest(Cell cell) {
        super(cell);
        addChest();
        this.setHealth(INITIAL_HEALTH);
    }

    @Override
    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.CHEST_OPEN);
        whoAmI = "open chest";
    }

    private void addChest() {
        chests.add(this);
    }

    public static List<Chest> getChests() {
        return chests;
    }

    @Override
    public String getTileName() {
        return whoAmI;
    }
}
