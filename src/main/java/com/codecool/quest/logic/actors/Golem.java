package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

public class Golem extends Actor implements Combative {

    private static final int INITIAL_HEALTH = 30;
    protected static final int INITIAL_ATTACK_DAMAGE = 2;
    private static final int INITIAL_ARMOR = 0;
    private static final int MONSTER_ATTACK_RANGE = 1;

    public Golem(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        this.setMonsterAttackRange(MONSTER_ATTACK_RANGE);
    }

    public void act() {
        if(isPlayerNexToIt()) {
            Actor target = getPlayerCurrentPosition().getActor();
            this.attack(target);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.STONES);
    }

    @Override
    public boolean hasFixedPosition() {
        return true;
    }

    @Override
    public String getTileName() {
        return "golem";
    }
}
