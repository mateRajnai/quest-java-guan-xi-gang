package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import java.util.Random;

public class Skeleton extends Actor {

    int MAX_MOVE_COORDINATE = 2;
    int MIN_MOVE_COORDINATE = -1;

    public Skeleton(Cell cell) {
        super(cell);
    }

    public int randomMoveCoordinate() {
        Random rand = new Random();
        int randomDirection = rand.nextInt(MAX_MOVE_COORDINATE - MIN_MOVE_COORDINATE) + MIN_MOVE_COORDINATE;
        return randomDirection;
    }

    public void move() {
        int dx = randomMoveCoordinate();
        int dy = randomMoveCoordinate();
        Cell nextCell = super.getCell().getNeighbor(dx, dy);
        if (!nextCell.getTileName().equals("wall") && nextCell.getActor() == null) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
