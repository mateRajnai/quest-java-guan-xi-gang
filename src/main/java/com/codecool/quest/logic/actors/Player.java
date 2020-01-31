package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.util.Direction;

public class Player extends Actor {

    public Player(Cell cell) {
        super(cell);
    }

    public void move(Direction direction) {
        Cell nextCell = cell.getNeighbour(direction);
        if (!nextCell.hasObstacle() && !nextCell.hasActor()) {
            cell.setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
    }

    public void move(int dx, int dy) {
        this.move(new Direction(dx, dy));
    }

    public String getTileName() {
        return "player";
    }
}
