package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Direction;

public class Octopus extends IntelligentFoe implements Vulnerable {

    private final static int INITIAL_HEALTH = 10;
    private final static int INITIAL_ATTACK_DAMAGE = 2;

    public Octopus(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
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
