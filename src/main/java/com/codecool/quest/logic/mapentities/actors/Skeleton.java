package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.CardinalDirection;
import com.codecool.quest.logic.util.Direction;

public class Skeleton extends IntelligentFoe implements Vulnerable {

    private static final int INITIAL_HEALTH = 10;
    private static final int INITIAL_ATTACK_DAMAGE = 2;

    private Direction patrolDirection = CardinalDirection.LEFT.get();

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    @Override
    public Direction calculateApproachVector() {
        Cell nextCell;
        for (CardinalDirection direction : CardinalDirection.values()) {
            nextCell = this.cell;
            do {
                nextCell = nextCell.getNeighbour(direction.get());
                if (nextCell.getActor() instanceof Player)
                    return direction.get();
            }
            while (nextCell.isTransparent() && !nextCell.isObstacle());
        }
        return null;
    }

    @Override
    public void patrol() {
        Cell nextCell = cell.getNeighbour(patrolDirection);
        if (canMoveTo(nextCell)) {
            moveTo(nextCell);
        } else if (nextCell.isObstacle()) {
            patrolDirection = patrolDirection.xFlipped();
            nextCell = cell.getNeighbour(patrolDirection);
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
