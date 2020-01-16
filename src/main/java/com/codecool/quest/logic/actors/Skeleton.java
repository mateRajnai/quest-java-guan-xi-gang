package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Actor {

    int coordinateSwitcher = -1;

    public Skeleton(Cell cell) {
        super(cell);
    }

    public void move() {
        int dx = coordinateSwitcher;
        int dy = 0;
        Cell nextCell = super.getCell().getNeighbor(dx, dy);

        if (nextCell.getTileName().equals("wall")) {
            coordinateSwitcher *= -1;
            dx = coordinateSwitcher;
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        if (!nextCell.getTileName().equals("wall") && nextCell.getActor() == null) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);
        }
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
