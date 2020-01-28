package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Hammer extends Item {

    private static List<Item> hammers = new ArrayList<>();

    public Hammer(Cell cell) {
        super(cell);
        addHammer();
    }

    @Override
    public List<Item> getInstances() {
        return hammers;
    }

    private void addHammer() {
        hammers.add(this);
    }

    @Override
    public String getTileName() {
        return "hammer";
    }
}
