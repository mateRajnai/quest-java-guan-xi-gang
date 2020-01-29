package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;

public class Golem extends Actor {

    private static final int INITIAL_HEALTH = 30;
    private static final int INITIAL_ATTACK_DAMAGE = 2;
    private static final int INITIAL_ARMOR = 0;
    private static final int MONSTER_ATTACK_RANGE = 1;

    private static List<Golem> golems = new ArrayList<>();

    public Golem(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        this.setMonsterAttackRange(MONSTER_ATTACK_RANGE);
    }

    public void attackIfPlayerNextToIt() {
        if(isPlayerNexToIt()) {
            Actor player = playerCurrentPosition.getActor();
            int modifiedDefenderHealth = handleAttack.attack(player.getHealth(), this.attackDamage);
            player.setHealth(modifiedDefenderHealth);
            handleAttack.isDead(player);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.STONES);
        golems.removeIf(golem -> golem == this);
    }

    public static void addGolem(Golem golem) {
        golems.add(golem);
    }

    public static List<Golem> getGolems() {
        return golems;
    }

    @Override
    public String getTileName() {
        return "golem";
    }
}
