package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;

    protected int health;
    protected int attackDamage;
    protected int armor;
    protected List<String> fixTiles = new ArrayList<>(Arrays.asList("wall", "bronze torch", "campfire", "chest open"));
    protected List<String> fixActors = new ArrayList<>(Arrays.asList("pot", "chest open", "chest closed"));
    protected static Cell playerCurrentPosition;
    public int monsterAttackRange;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {}

    protected void moveTo(Cell nextCell) {
        this.cell.setActor(null);
        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    public int getMonsterAttackRange() {
        return monsterAttackRange;
    }

    public void setMonsterAttackRange(int monsterAttackRange) {
        this.monsterAttackRange = monsterAttackRange;
    }

    public int attack(int targetHealth) {
        return targetHealth - this.attackDamage;
    }

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int newHealth) {this.health = newHealth;}

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(int newAttackDamage) {this.attackDamage = newAttackDamage;}

    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int newArmor) {this.armor = newArmor;}

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        cell.setActor(this);
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setPlayerCurrentPosition(Cell playerCurrentPosition) {
        Actor.playerCurrentPosition = playerCurrentPosition;
    }

    public Cell getPlayerCurrentPosition() {
        return playerCurrentPosition;
    }


    public boolean isPlayerNexToIt() {
        int[] xDirections = {1, 0, -1, 0};
        int[] yDirections = {0, 1, 0, -1};

        for (int index = 0; index < xDirections.length; index++) {
            Cell nextCell = this.getCell().getNeighbor(xDirections[index], yDirections[index]);

            if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("player")) {
                return true;
            }
        }
        return false;
    }

    protected void attack(Actor target) {
        target.setHealth(target.getHealth() - this.getAttackDamage());
        if(isDead(target)) {
            target.terminate();
        }
    }

    private boolean isDead(Actor target) {
        return target.getHealth() <= 0;
    }

    public abstract void terminate();

    public boolean isPlayerNear() {
        return Math.abs(this.getX() - playerCurrentPosition.getX()) <= monsterAttackRange &&
                Math.abs(this.getY() - playerCurrentPosition.getY()) <= monsterAttackRange;
    }



}
