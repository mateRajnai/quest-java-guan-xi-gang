package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Skeleton extends Actor {

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;

    private Direction direction = new Direction(-1, 0);
    private static List<Skeleton> skeletons = new ArrayList<>();

    public Skeleton(Cell cell) {
        super(cell);
        addSkeleton();
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void move() {
        Cell nextCell = this.getCell().getNeighbor(direction);

        if (!nextCell.getType().isTraversable() || nextCell.hasFixedActor()) {
            direction = direction.xFlipped();
            nextCell = super.getCell().getNeighbor(direction);
        }

        if (!nextCell.isBlocking())
            moveTo(nextCell);
        else if (nextCell.getActor() instanceof Player)
            this.attack(nextCell.getActor());
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
        skeletons.removeIf(skeleton -> skeleton == this);
    }

    private void addSkeleton() {
        skeletons.add(this);
    }

    public static List<Skeleton> getSkeletons() {
        return skeletons;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
