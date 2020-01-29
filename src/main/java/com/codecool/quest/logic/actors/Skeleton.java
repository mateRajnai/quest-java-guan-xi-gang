package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;
import java.util.spi.AbstractResourceBundleProvider;

public class Skeleton extends Actor {

    HandleAttack handleAttack = new HandleAttack();
    AutoTarget autotarget = new AutoTarget(MONSTER_ATTACK_RANGE, this);

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;
    private int dx;
    private int dy;

    private int coordinateSwitcher = -1;
    private static List<Skeleton> skeletons = new ArrayList<>();

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void move() {

        Cell nextCell;

        //if player is near it will search for the next closest possible cell
        if(isPlayerNear()) {
            nextCell= autotarget.getClosestCellToPlayer();

            //step on nextCell if possible
            if (!this.isPlayerNexToIt() && !fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() == null) {
                stepOnNextCell(nextCell);
            } else {
                attackPlayer(playerCurrentPosition);
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
            stepOnNextCell(nextCell);


            //if player on the nextcell monster will attack it
        } else if (!fixTiles.contains(nextCell.getTileName()) &&
                nextCell.getActor() != null &&
                nextCell.getActor().getTileName().equals("player")) {

            attackPlayer(nextCell);

        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
        skeletons.removeIf(skeleton -> skeleton == this);
    }

    private void stepOnNextCell(Cell nextCell) {
        super.getCell().setActor(null);
        nextCell.setActor(this);
        super.setCell(nextCell);
    }

    private void attackPlayer(Cell nextCell) {
        int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
        nextCell.getActor().setHealth(modifiedDefenderHealth);
        handleAttack.isDead(modifiedDefenderHealth, nextCell);
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
