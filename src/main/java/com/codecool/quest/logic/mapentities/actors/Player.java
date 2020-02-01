package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.util.Direction;

public class Player extends Combatant {

    public Player(Cell cell) {
        super(cell);
    }

    public void move(Direction direction) {
        Cell nextCell = cell.getNeighbour(direction);
        if (!nextCell.hasObstacle() && !nextCell.getMapEntity() instanceof ) {
            moveTo(nextCell);
        }
    }

    public void move(int dx, int dy) {
        this.move(new Direction(dx, dy));
    }

    public String getTileName() {
        return "player";
    }
}
