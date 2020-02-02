package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.CellSurroundings;
import com.codecool.quest.logic.util.Direction;

import java.util.List;

public class Octopus extends IntelligentFoe implements Vulnerable {

    private final static int INITIAL_HEALTH = 10;
    private final static int INITIAL_ATTACK_DAMAGE = 2;
    private final static int DETECTION_BOX_SIZE = 3;

    private List<Cell> surroundings;

    public Octopus(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        surroundings = new CellSurroundings(this.cell, DETECTION_BOX_SIZE).getCells();
    }

    @Override
    public Direction calculateApproachVector() {
        return null;
    }

    @Override
    public void patrol() {

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
