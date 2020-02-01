package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;

public abstract class Combatant extends Actor {

    protected int attackDamage;
    protected int health;

    public Combatant(Cell cell) {
        super(cell);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void hit(Combatant combatant) {
        combatant.setHealth(Math.max(combatant.getHealth() - this.attackDamage, 0));
    }

    public boolean isDead() {
        return this.health == 0;
    }
}
