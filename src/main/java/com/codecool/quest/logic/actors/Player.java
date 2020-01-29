package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Player extends Actor {

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 5;
    private static final int INITIAL_ARMOR = 0;

    public Player(Cell cell) {
        super(cell);
        setPlayerCurrentPosition(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }


    @Override
    public void move(int dx, int dy) {
        Cell nextCell = super.getCell().getNeighbor(dx, dy);
        if (nextCell == null) return;

        if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() == null) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            setPlayerCurrentPosition(nextCell);
            super.setCell(nextCell);

        } else if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() != null) {
            Actor target = nextCell.getActor();
            this.attack(target);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
    }

    public String getTileName() {
        return "player";
    }
}
