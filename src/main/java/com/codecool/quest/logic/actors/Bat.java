package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;

public class Bat extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 6;
    private static final int INITIAL_ATTACK_DAMAGE = 1;
    private static final int INITIAL_ARMOR = 0;

    private static final int[] direction = new int[]{1,1};
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

        if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() == null) {
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

        //wall on corner
        nextCell = super.getCell().getNeighbor(0, dy);
        Cell nextCellSide = super.getCell().getNeighbor(dx, 0);
        if(nextCell.getTileName().equals("wall") && nextCellSide.getTileName().equals("wall")) {
            direction[0] *= -1;
            direction[1] *= -1;
            dx = direction[0];
            dy = direction[1];
        }

        nextCell = super.getCell().getNeighbor(dx, dy);
        if (nextCell.getTileName().equals("wall")) {

            //wall on right side
            nextCell = super.getCell().getNeighbor(dx, 0);
            if(nextCell.getTileName().equals("wall")) {
                direction[0] *= -1;
                dx = direction[0];
            }

            //wall on left side
            nextCell = super.getCell().getNeighbor(dx, 0);
            if(nextCell.getTileName().equals("wall")) {
                direction[0] *= -1;
                dx = direction[0];
            }

            //wall on bottom
            nextCell = super.getCell().getNeighbor(0, dy);
            if(nextCell.getTileName().equals("wall")) {
                direction[1] *= -1;
                dy = direction[1];
            }

            //wall on top
            nextCell = super.getCell().getNeighbor(0, dy);
            if(nextCell.getTileName().equals("wall")) {
                direction[1] *= -1;
                dy = direction[1];
            }

            //wall on inner corner
            nextCell = super.getCell().getNeighbor(dx, dy);
            if(nextCell.getTileName().equals("wall")) {
                direction[0] *= -1;
                direction[1] *= -1;
                dx = direction[0];
                dy = direction[1];
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
