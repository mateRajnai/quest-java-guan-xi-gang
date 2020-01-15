package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Golem extends Actor {

    private static final int INITIAL_HEALTH = 30;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;

    private static List<Golem> golems = new ArrayList<>();

    public Golem(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void terminate() {
        this.getCell().setActor(null);
        golems.removeIf(golem -> golem == this);
    }

    public static void addGolem(Golem golem) {
        golems.add(golem);
    }

    public List<Golem> getGolems() {
        return golems;
    }

    @Override
    public String getTileName() {
        return "golem";
    }
}
