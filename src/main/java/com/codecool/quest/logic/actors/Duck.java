package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Duck extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 5;
    private static final int INITIAL_ATTACK_DAMAGE = 0;
    private static final int INITIAL_ARMOR = 0;

    public static final int MAX_MOVE_COORDINATE = 2;
    public static final int MIN_MOVE_COORDINATE = -1;
    public static final int[] direction = new int[]{1,1};

    private static List<Duck> ducks = new ArrayList<>();

    public Duck(Cell cell) {
        super(cell);
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
        if(nextCell.getTileName().equals("wall") && nextCellSide.getTileName().equals("wall")) {
            direction[0] *= -1;
            direction[1] *= -1;
            dx = direction[0];
            dy = direction[1];
        } else {
            nextCell = super.getCell().getNeighbor(dx, dy);
        }

        if (!fixTiles.contains(nextCell.getTileName()) && nextCell.getActor() == null) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);

        } else if (!fixTiles.contains(nextCell.getTileName()) &&
                nextCell.getActor() != null &&
                nextCell.getActor().getTileName().equals("player")) {

            int modifiedDefenderHealth = handleAttack.attack(nextCell.getActor().getHealth(), this.attackDamage);
            nextCell.getActor().setHealth(modifiedDefenderHealth);
            handleAttack.isDead(modifiedDefenderHealth, nextCell);
        }
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.BONE);
        ducks.removeIf(duck -> duck == this);
    }

    public static void addDuck(Duck duck) {
        ducks.add(duck);
    }

    public static List<Duck> getDucks() {
        return ducks;
    }

    @Override
    public String getTileName() {
        return "duck";
    }
}
