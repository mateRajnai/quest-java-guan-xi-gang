package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Automaton;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.CardinalDirection;
import com.codecool.quest.logic.util.Direction;

public abstract class IntelligentFoe extends Foe implements Automaton {

    public IntelligentFoe(Cell cell) {
        super(cell);
    }

    @Override
    public void operate() {
        Cell targetCell = searchAdjacentCells();
        if (canHit(targetCell)) {
            hit((Vulnerable) targetCell.getActor());
        } else {
            Direction direction = calculateApproachVector();
            if (canDetectPlayer(direction))
                approach(direction);
            else
                patrol();
        }
    }

    public Cell searchAdjacentCells() {
        Cell adjacentCell;
        for (CardinalDirection direction : CardinalDirection.values()) {
            adjacentCell = this.cell.getNeighbour(direction.get());
            if (adjacentCell.getActor() instanceof Player)
                return adjacentCell;
        }
        return null;
    }

    public abstract Direction calculateApproachVector();

    public boolean canDetectPlayer(Direction approachDirection) {
        return approachDirection != null;
    }

    public void approach(Direction direction) {
        Cell nextCell = this.cell.getNeighbour(direction);
        if (this.canMoveTo(nextCell))
            this.moveTo(nextCell);
    }

    public abstract void patrol();
}
