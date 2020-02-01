package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.MapEntity;
import com.codecool.quest.logic.mapentities.Vulnerable;

public abstract class Actor extends MapEntity {

    protected int health;
    protected int attackDamage;

    public Actor(Cell cell) {
        super(cell);
    }

    public void moveTo(Cell cell) {
        this.cell.setMapEntity(null);
        cell.setMapEntity(this);
        this.setCell(cell);
    }

    public boolean canMoveTo(Cell cell) {
        return !cell.isObstacle() && !(cell.getMapEntity() instanceof Actor);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public abstract boolean canHit(Cell cell);

    public void hit(Vulnerable target) {
        target.setHealth(Math.max(target.getHealth() - this.attackDamage, 0));
    }

    public boolean isDead() {
        return this.health <= 0;
    }
}
