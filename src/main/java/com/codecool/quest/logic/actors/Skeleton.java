package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;

public class Skeleton extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;

    private int coordinateSwitcher = -1;
    private static List<Skeleton> skeletons = new ArrayList<>();

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void move() {
        int dx = coordinateSwitcher;
        int dy = 0;
        Cell nextCell = super.getCell().getNeighbor(dx, dy);

        if (fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {
            coordinateSwitcher *= -1;
            dx = coordinateSwitcher;
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() == null) {

            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);

        } else if (!fixTiles.contains(nextCell.getTileName()) &&
                nextCell.getActor() != null &&
                nextCell.getActor().getTileName().equals("player")) {

            int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
            nextCell.getActor().setHealth(modifiedDefenderHealth);
            handleAttack.isDead(modifiedDefenderHealth, nextCell);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
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

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
