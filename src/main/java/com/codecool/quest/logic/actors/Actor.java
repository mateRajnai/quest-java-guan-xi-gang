package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;

    protected int health;
    protected int attackDamage;
    protected int armor;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
    };

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

    public void setCell(Cell cell) { this.cell = cell;}

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public abstract void terminate();
}
