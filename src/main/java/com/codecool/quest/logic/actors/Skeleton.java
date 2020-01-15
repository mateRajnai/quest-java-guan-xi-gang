package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;

public class Skeleton extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 5;
    private static final int INITIAL_ARMOR = 0;
    private static final String CHARACTER_TYPE = "skeleton";

    private static int COORDINATE_SWITCHER = -1;
    private static List<Skeleton> skeletons = new ArrayList<>();

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void move() {
        int dx = COORDINATE_SWITCHER;
        int dy = 0;
        Cell nextCell = super.getCell().getNeighbor(dx, dy);

        if (nextCell.getTileName().equals("wall")) {
            COORDINATE_SWITCHER *= -1;
            dx = COORDINATE_SWITCHER;
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        if (!nextCell.getTileName().equals("wall") && nextCell.getActor() == null) {

            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);

        } else if (!nextCell.getTileName().equals("wall") &&
                nextCell.getActor() != null &&
                nextCell.getActor().getWhoAmI().equals("player")) {

            int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
            nextCell.getActor().setHealth(modifiedDefenderHealth);
            handleAttack.isDead(modifiedDefenderHealth, nextCell);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        skeletons.removeIf(skeleton -> skeleton == this);
    }

    public static void addSkeleton(Skeleton skeleton) {
        skeletons.add(skeleton);
    }

    public static List<Skeleton> getSkeletons() {
        return skeletons;
    }

    @Override
    public void move(int dx, int dy) {

    }

    public String getWhoAmI() {
        return CHARACTER_TYPE;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
