package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class Skeleton extends Actor {

    AutoTarget autotarget = new AutoTarget(this.monsterAttackRange, this);

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;
    private static final int MONSTER_ATTACK_RANGE = 3;
    private int dx;
    private int dy;

    private int coordinateSwitcher = -1;
    private static List<Skeleton> skeletons = new ArrayList<>();

    public Skeleton(Cell cell) {
        super(cell);
        addSkeleton();
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        this.setMonsterAttackRange(MONSTER_ATTACK_RANGE);
    }

    public void move() {

        Cell nextCell;

        //if player is near it will search for the next closest possible cell
        if(isPlayerNear()) {
            nextCell= autotarget.getClosestCellToPlayer();

            //step on nextCell if possible
            if (!nextCell.isBlocking()) {
                this.moveTo(nextCell);
            } else {
                Actor target = getPlayerCurrentPosition().getActor();
                this.attack(target);
            }
        }
        //if player is not near it will do the standard movement
        else {
            dx = coordinateSwitcher;
            dy = 0;
            nextCell = this.getCell().getNeighbor(dx, dy);
            standardMovement(nextCell);
        }

    }

    private void standardMovement(Cell nextCell) {

        //bounce if the nexcell is wall
        if (fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {
            coordinateSwitcher *= -1;
            dx = coordinateSwitcher;
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        //step on nextCell if possible
        if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() == null) {
            this.moveTo(nextCell);


            //if player on the nextcell monster will attack it
        } else if (!fixTiles.contains(nextCell.getTileName()) &&
                nextCell.getActor() != null &&
                nextCell.getActor().getTileName().equals("player")) {

            Actor target = getPlayerCurrentPosition().getActor();
            this.attack(target);

        }
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
    public void move(int dx, int dy) {

    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
