package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.mapentities.Automaton;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Direction;

public abstract class IntelligentFoe extends Foe implements Automaton {
    GameMap map;

    public IntelligentFoe(Cell cell) {
        super(cell);
        this.map = cell.getGameMap();
    }

    @Override
    public void operate() {
        Cell targetCell = searchSurroundings();
        if (targetCell != null && canHit(targetCell)) {
            hit((Vulnerable) targetCell.getMapEntity());
        } else {
            Direction vector = calculateVectorTowardsPlayer();
            if (canDetectPlayer(vector))
                approach(vector);
            else
                patrol();
        }
    }

    public Cell searchSurroundings() {
        return null;
    }

    private Direction calculateVectorTowardsPlayer() {
        return new Direction(map.getPlayer().getX() - this.getX(), map.getPlayer().getY() - this.getY());
    }

    public boolean canDetectPlayer(Direction vector) {
        return false;
    }

    public abstract void approach(Direction vector);

    public abstract void patrol();
}
