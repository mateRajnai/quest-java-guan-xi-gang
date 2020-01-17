package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

public class Player extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 5;
    private static final int INITIAL_ARMOR = 0;

    public Player(Cell cell) {
        super(cell);
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
                super.setCell(nextCell);

        } else if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() != null) {
            int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
            nextCell.getActor().setHealth(modifiedDefenderHealth);
            handleAttack.isDead(modifiedDefenderHealth, nextCell);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
    }

    public String getTileName() {
        return "player";
    }

    public boolean isDoorInNeighbourCell() {
        return getCell().getNeighbor(1, 0).getTileName().equals("door closed") ||
                getCell().getNeighbor(-1, 0).getTileName().equals("door closed") ||
                getCell().getNeighbor(0, 1).getTileName().equals("door closed") ||
                getCell().getNeighbor(0, -1).getTileName().equals("door closed");
    }

    public void openDoorInNeighbourCell() {
        if (getCell().getNeighbor(1, 0).getTileName().equals("door closed"))
            getCell().getNeighbor(1, 0).setType(CellType.DOOR_OPENED);
        else if (getCell().getNeighbor(-1, 0).getTileName().equals("door closed"))
            getCell().getNeighbor(-1, 0).setType(CellType.DOOR_OPENED);
        else if (getCell().getNeighbor(0, 1).getTileName().equals("door closed"))
            getCell().getNeighbor(0, 1).setType(CellType.DOOR_OPENED);
        else if (getCell().getNeighbor(0, -1).getTileName().equals("door closed"))
            getCell().getNeighbor(0, -1).setType(CellType.DOOR_OPENED);
    }
}
