package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bat extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 6;
    private static final int INITIAL_ATTACK_DAMAGE = 1;
    private static final int INITIAL_ARMOR = 0;

    private int[] direction = new int[]{1,1};
    private static List<Bat> bats = new ArrayList<>();


    public Bat(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void move() {

        int dx = direction[0];
        int dy = direction[1];

        Cell nextCell = wallBounceCheck(dx, dy);

        if (!fixTiles.contains(nextCell.getTileName()) &&
                nextCell.getActor() == null &&
                !fixActors.contains(nextCell.getTileName())) {

            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);

        }  else if (!fixTiles.contains(nextCell.getTileName()) &&
                nextCell.getActor() != null &&
                nextCell.getActor().getTileName().equals("player")) {

            int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
            nextCell.getActor().setHealth(modifiedDefenderHealth);
            handleAttack.isDead(modifiedDefenderHealth, nextCell);
        }
    }


    public Cell wallBounceCheck(int dx, int dy) {
        Cell nextCell;
        Cell otherSideCell;
        boolean rightLeftSideCheck;
        boolean topBottomSideCheck;

        //check if it hits the wall
        nextCell = super.getCell().getNeighbor(dx, dy);
        if (fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

            //check if it hits wall on right or left side
            nextCell = super.getCell().getNeighbor(dx, 0);
            if(fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

                direction[0] *= -1;
                dx = direction[0];
                rightLeftSideCheck = true;
            } else {
                rightLeftSideCheck = false;
            }

            //check if it hits wall on bottom or top
            nextCell = super.getCell().getNeighbor(0, dy);
            if(fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

                direction[1] *= -1;
                dy = direction[1];
                topBottomSideCheck = true;
            } else {
                topBottomSideCheck = false;
            }

            //check if stuck between right and left walls
            nextCell = super.getCell().getNeighbor(0, dy);
            otherSideCell = super.getCell().getNeighbor(dx, 0);
            if (rightLeftSideCheck &&
                    !fixTiles.contains(nextCell.getTileName()) &&
                    nextCell.getActor() == null &&
                    !fixActors.contains(nextCell.getTileName()) &&
                    (fixTiles.contains(otherSideCell.getTileName()) ||
                    fixActors.contains(otherSideCell.getTileName())) &&
                    otherSideCell.getActor() == null) {

                dx = 0;
            }

            //check if stuck between top and bottom walls
            nextCell = super.getCell().getNeighbor(dx, 0);
            otherSideCell = super.getCell().getNeighbor(0, dy);
            if (topBottomSideCheck &&
                    !fixTiles.contains(nextCell.getTileName()) &&
                    nextCell.getActor() == null &&
                    !fixActors.contains(nextCell.getTileName()) &&
                    (fixTiles.contains(otherSideCell.getTileName()) ||
                    fixActors.contains(otherSideCell.getTileName())) &&
                    otherSideCell.getActor() == null){

                dy = 0;
            }

            //check if hits corner
            if (topBottomSideCheck && rightLeftSideCheck) {
                Cell rightLeftContinueCheck = super.getCell().getNeighbor(dx * -1, dy);
                Cell topDownContinueCheck = super.getCell().getNeighbor(dx, dy * -1);

                //check if it can escape right or left
                if (!fixTiles.contains(rightLeftContinueCheck.getTileName()) &&
                        !fixActors.contains(rightLeftContinueCheck.getTileName())) {

                    direction[0] *= -1;
                    dx = direction[0];
                }

                //check if it can escape top or bottom
                if (!fixTiles.contains(topDownContinueCheck.getTileName()) &&
                        !fixActors.contains(topDownContinueCheck.getTileName())) {

                    direction[1] *= -1;
                    dy = direction[1];
                }
            }

            //check if it hits wall on inner corner
            nextCell = super.getCell().getNeighbor(dx, dy);
            if(fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

                direction[0] *= -1;
                direction[1] *= -1;
                dx = direction[0];
                dy = direction[1];

                //check if it hits a wall and the next step is inner corner wall, it goes back, instead of waiting one turn
                if(topBottomSideCheck) {
                    direction[1] *= -1;
                    dy = direction[1];
                }

                if(rightLeftSideCheck) {
                    direction[0] *= -1;
                    dx = direction[0];
                }

            }
            nextCell = super.getCell().getNeighbor(dx, dy);
        }
        return nextCell;
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.BONE);
        bats.removeIf(bat -> bat == this);
    }

    public static void addBat(Bat bat) {
        bats.add(bat);
    }

    public static List<Bat> getBats() {
        return bats;
    }

    @Override
    public String getTileName() {
        return "bat";
    }

}
