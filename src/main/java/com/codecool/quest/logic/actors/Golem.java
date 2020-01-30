package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class Golem extends Actor {

    private static final int INITIAL_HEALTH = 30;
    protected static final int INITIAL_ATTACK_DAMAGE = 2;
    private static final int INITIAL_ARMOR = 0;
    private static final int MONSTER_ATTACK_RANGE = 1;

    private static List<Golem> golems = new ArrayList<>();

    public Golem(Cell cell) {
        super(cell);
        addGolem();
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        this.setMonsterAttackRange(MONSTER_ATTACK_RANGE);
    }

    @Override
    public void move() {
    }

    public void attackIfPlayerNextToIt() {
        if(isPlayerNexToIt()) {
            Actor target = getPlayerCurrentPosition().getActor();
            this.attack(target);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.STONES);
        golems.removeIf(golem -> golem == this);
    }

    private void addGolem() {
        golems.add(this);
    }

    public static List<Golem> getGolems() {
        return golems;
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
