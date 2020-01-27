package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class HandleAttack {

    public HandleAttack(){

    }

    public int attack(int targetHealth, int attackDamage) {
        return targetHealth - attackDamage;
    }

    public void isDead(int targetHealth, Cell targetCell) {
        if(targetHealth <= 0) {
            killTarget(targetCell);
        }
    }

    private void killTarget(Cell targetCell) {
        Actor target = targetCell.getActor();
        target.terminate();
    }
}
