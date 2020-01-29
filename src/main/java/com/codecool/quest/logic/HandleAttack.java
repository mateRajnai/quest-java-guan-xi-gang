package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;

public class HandleAttack {

    public HandleAttack(){

    }

    public int attack(int targetHealth, int attackDamage) {
        return targetHealth - attackDamage;
    }

    public void isDead(Actor target) {
        if(target.getHealth() <= 0) {
            killTarget(target.getCell());
        }
    }

    private void killTarget(Cell targetCell) {
        Actor target = targetCell.getActor();
        target.terminate();
    }
}
