package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Duck extends Actor {

    private static final int INITIAL_HEALTH = 5;
    private static final int INITIAL_ATTACK_DAMAGE = 0;
    private static final int INITIAL_ARMOR = 0;

    public static final int MAX_MOVE_COORDINATE = 2;
    public static final int MIN_MOVE_COORDINATE = -1;
    public static final int[] direction = new int[]{1,1};

    private static List<Duck> ducks = new ArrayList<>();

    public Duck(Cell cell) {
        super(cell);
        addDuck();
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public int randomMoveCoordinate() {
        Random rand = new Random();
        return rand.nextInt(MAX_MOVE_COORDINATE - MIN_MOVE_COORDINATE) + MIN_MOVE_COORDINATE;
    }

    public void move() {
        int dx = randomMoveCoordinate();
        int dy = randomMoveCoordinate();
        Cell nextCell;

        //wall on corner check and send set the actors direction to opposite
        nextCell = super.getCell().getNeighbor(0, dy);
        Cell nextCellSide = super.getCell().getNeighbor(dx, 0);
        if(!nextCell.isBlocking()) {
            direction[0] *= -1;
            direction[1] *= -1;
            dx = direction[0];
            dy = direction[1];
        } else {
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        if (!nextCell.isBlocking()) {
            this.moveTo(nextCell);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.BONE);
        ducks.removeIf(duck -> duck == this);
    }

    private void addDuck() {
        ducks.add(this);
    }

    public static List<Duck> getDucks() {
        return ducks;
    }

    @Override
    public String getTileName() {
        return "duck";
    }
}
