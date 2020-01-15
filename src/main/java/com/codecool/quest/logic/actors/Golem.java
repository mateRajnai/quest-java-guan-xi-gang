package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;

public class Golem extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 30;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;
    private static final String CHARACTER_TYPE = "golem";

    private static List<Golem> golems = new ArrayList<>();

    public Golem(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void attackIfPlayerNextToIt() {
        int[] xDirections = {1, 0, -1, 0};
        int[] yDirections = {0, 1, 0, -1};

        for (int index = 0; index < xDirections.length; index++) {
            Cell nextCell = super.getCell().getNeighbor(xDirections[index], yDirections[index]);

            if (nextCell.getActor() != null && nextCell.getActor().getWhoAmI().equals("player")) {
                int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
                nextCell.getActor().setHealth(modifiedDefenderHealth);
                handleAttack.isDead(modifiedDefenderHealth, nextCell);
            }
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        golems.removeIf(golem -> golem == this);
    }

    public static void addGolem(Golem golem) {
        golems.add(golem);
    }

    public static List<Golem> getGolems() {
        return golems;
    }

    public String getWhoAmI() {
        return CHARACTER_TYPE;
    }

    @Override
    public String getTileName() {
        return "golem";
    }
}
