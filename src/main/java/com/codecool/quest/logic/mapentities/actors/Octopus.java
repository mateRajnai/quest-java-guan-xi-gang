package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.CardinalDirection;
import com.codecool.quest.logic.util.CellSurroundings;
import com.codecool.quest.logic.util.Direction;

import java.util.List;

public class Octopus extends IntelligentFoe implements Vulnerable {

    private final static int INITIAL_HEALTH = 10;
    private final static int INITIAL_ATTACK_DAMAGE = 2;
    private final static int DETECTION_BOX_SIZE = 3;

    private Direction patrolDirection = CardinalDirection.UP.get();

    public Octopus(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    @Override
    public Direction calculateApproachVector() {
        Cell playerCell = searchPlayerInSurroundings();
        if (playerCell == null) return null;

        Cell nextCell = calculateClosestCellToPlayer(playerCell);
        if (nextCell == null) return null;

        return this.cell.directionTo(nextCell);
    }

    private Cell searchPlayerInSurroundings() {
        List<Cell> surroundings = CellSurroundings.collect(this.cell, DETECTION_BOX_SIZE);
        Cell playerCell = null;
        for (Cell surroundingCell : surroundings) {
            if (surroundingCell.getActor() instanceof Player) {
                playerCell = surroundingCell;
                break;
            }
        }
        return playerCell;
    }

    private Cell calculateClosestCellToPlayer(Cell playerCell) {
        Cell tempCell, nextCell = null;
        int distance, minDistance = -1;
        for (CardinalDirection cardinalDirection : CardinalDirection.values()) {
            tempCell = this.cell.getNeighbour(cardinalDirection);
            if (!this.canMoveTo(tempCell)) continue;

            distance = tempCell.distanceFrom(playerCell);
            if (minDistance == -1 || distance < minDistance) {
                minDistance = distance;
                nextCell = tempCell;
            }
        }
        return nextCell;
    }

    @Override
    public void patrol() {
        Cell nextCell = cell.getNeighbour(patrolDirection);
        if (canMoveTo(nextCell)) {
            moveTo(nextCell);
        } else if (nextCell.isObstacle()) {
            patrolDirection = patrolDirection.yFlipped();
            nextCell = cell.getNeighbour(patrolDirection);
            if (canMoveTo(nextCell))
                moveTo(nextCell);
        }
    }

    @Override
    public void terminate() {
        this.cell.setActor(null);
        this.setCell(null);
    }

    @Override
    public String getTileName() {
        return "octopus";
    }
}
