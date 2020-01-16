package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Golem extends Actor {

    public Golem(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "golem";
    }
}
