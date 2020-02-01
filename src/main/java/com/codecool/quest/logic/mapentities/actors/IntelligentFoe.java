package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Automaton;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Vector;

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
            Vector vector = calculateApproachVector();
            if (canDetectPlayer(vector))
                approach(vector);
            else
                patrol();
        }
    }

    public Cell searchAdjacentCells() {
        Cell adjacentCell;
        for (Vector vector : Vector.MAIN_VECTORS) {
            adjacentCell = this.cell.getNeighbour(vector);
            if (adjacentCell.getActor() instanceof Player)
                return adjacentCell;
        }
        return null;
    }

    public abstract Vector calculateApproachVector();

    public boolean canDetectPlayer(Vector approachVector) {
        return approachVector != null;
    }

    public void approach(Vector vector) {
        this.moveTo(this.cell.getNeighbour(vector));
    }

    public abstract void patrol();
}
