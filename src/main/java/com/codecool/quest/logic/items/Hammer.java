package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Hammer extends Item {

    public Hammer(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "hammer";
    }
}
