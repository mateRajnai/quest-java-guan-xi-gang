package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;

public class Player extends Actor {

    private static Player player;
    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 5;
    private static final int INITIAL_ARMOR = 0;

    public Player(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        player = this;
    }

    public static Player getInstance() {
        return player;
    }

    public void move(int dx, int dy) {
        this.move(new Direction(dx, dy));
    }

    public void move(Direction direction) {
        Cell nextCell = this.getCell().getNeighbor(direction);
        if (nextCell == null) return;

        if (!nextCell.isBlocking())
            this.moveTo(nextCell);
        else if (nextCell.hasActor())
            this.attack(nextCell.getActor());
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
