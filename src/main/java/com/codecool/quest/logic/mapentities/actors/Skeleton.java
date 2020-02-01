package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Vector;

public class Skeleton extends IntelligentFoe implements Vulnerable {

    private static final int INITIAL_HEALTH = 10;
    private static final int INITIAL_ATTACK_DAMAGE = 2;

    private Vector patrolVector = Vector.LEFT;

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    @Override
    public Vector calculateApproachVector() {
        Cell nextCell;
        for (Vector vector : Vector.MAIN_VECTORS) {
            nextCell = this.cell;
            do {
                nextCell = nextCell.getNeighbour(vector);
                if (nextCell.getActor() instanceof Player)
                    return vector;
            }
            while (nextCell.isTransparent() && !nextCell.isObstacle());
        }
        return null;
    }

    @Override
    public void patrol() {
        Cell nextCell = cell.getNeighbour(patrolVector);
        if (canMoveTo(nextCell)) {
            moveTo(nextCell);
        } else if (nextCell.isObstacle()) {
            patrolVector = patrolVector.xFlipped();
            nextCell = cell.getNeighbour(patrolVector);
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
