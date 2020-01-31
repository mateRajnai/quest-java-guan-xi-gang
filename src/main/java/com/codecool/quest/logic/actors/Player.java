package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = super.getCell().getNeighbour(dx, dy);
        if (!nextCell.getTileName().equals("wall") && nextCell.getActor() == null) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);
        }
    }

    public String getTileName() {
        return "player";
    }
}
