package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

import java.util.Random;

public class Duck extends Actor {

    int MAX_MOVE_COORDINATE = 2;
    int MIN_MOVE_COORDINATE = -1;
    int[] direction = new int[]{1,1};

    public Duck(Cell cell) {
        super(cell);
    }

    public int randomMoveCoordinate() {
        Random rand = new Random();
        return rand.nextInt(MAX_MOVE_COORDINATE - MIN_MOVE_COORDINATE) + MIN_MOVE_COORDINATE;
    }

    public void move() {
        int dx = randomMoveCoordinate();
        int dy = randomMoveCoordinate();
        Cell nextCell;

        //wall on corner check and send set the actors direction to opposite
        nextCell = super.getCell().getNeighbor(0, dy);
        Cell nextCellSide = super.getCell().getNeighbor(dx, 0);
        if(nextCell.getTileName().equals("wall") && nextCellSide.getTileName().equals("wall")) {
            direction[0] *= -1;
            direction[1] *= -1;
            dx = direction[0];
            dy = direction[1];
        } else {
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
        return "duck";
    }
}
