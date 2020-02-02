package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Direction;

public class Troll extends IntelligentFoe implements Vulnerable {

    private static final int INITIAL_HEALTH = 15;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int DETECTION_RANGE = 2;

    private Direction patrolDirection = Direction.RIGHT;

    public Troll(Cell cell) {
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
        this.cell.setItem(null);
        this.setCell(null);
    }

    @Override
    public String getTileName() {
        return "troll";
    }
}
