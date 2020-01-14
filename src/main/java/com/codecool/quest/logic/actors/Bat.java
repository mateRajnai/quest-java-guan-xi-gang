package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Bat extends Actor {

    int[] coordinates = new int[]{1,1};
    int[] bouncer = new int[2];

    public Bat(Cell cell) {
        super(cell);
    }

    public void move() {
        int dx = coordinates[0];
        int dy = coordinates[1];
        Cell nextCell = super.getCell().getNeighbor(dx, dy);

        if (nextCell.getTileName().equals("wall")) {
            bouncer[1] *=
            dy = bouncer[0];
            dx = bouncer[1];
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        if (!nextCell.getTileName().equals("wall") && nextCell.getActor() == null) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);
        }
    }

    @Override
    public String getTileName() {
        return "bat";
    }
}
