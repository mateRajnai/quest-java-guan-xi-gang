package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Direction;

public class Skeleton extends IntelligentFoe implements Vulnerable {

    private static final int INITIAL_HEALTH = 10;
    private static final int INITIAL_ATTACK_DAMAGE = 2;

    private Direction direction = Direction.LEFT;

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    @Override
    public void approach(Direction vector) {

    }

    @Override
    public void patrol() {
        Cell nextCell = cell.getNeighbour(direction);
        if (canMoveTo(nextCell)) {
            moveTo(nextCell);
        } else if (nextCell.isObstacle()) {
            direction = direction.xFlipped();
            nextCell = cell.getNeighbour(direction);
            if (canMoveTo(nextCell))
                moveTo(nextCell);
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void terminate() {
        this.cell.setItem(null);
        this.setCell(null);
    }
}
