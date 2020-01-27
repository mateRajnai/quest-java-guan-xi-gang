package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.List;

public class Bat extends Actor {

    HandleAttack handleAttack = new HandleAttack();

    private static final int INITIAL_HEALTH = 6;
    private static final int INITIAL_ATTACK_DAMAGE = 1;
    private static final int INITIAL_ARMOR = 0;

    private Direction direction = new Direction(1, 1);
    private static List<Bat> bats = new ArrayList<>();


    public Bat(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public void move() {
        Cell nextCell = this.getCell().getNeighbor(direction);

        if (nextCell.getActor() instanceof Player)
            handleAttack.attackPlayer(this, nextCell);
        else
            moveToNewCell(nextCell);
    }

    private void moveToNewCell(Cell defaultCell) {
        chooseDirection(defaultCell);
        Cell nextCell = this.getCell().getNeighbor(direction);
        this.getCell().setActor(null);
        this.setCell(nextCell);
    }

    private void chooseDirection(Cell defaultCell) {
        Cell yNeighbor = this.getCell().getNeighbor(direction.yComponent());
        Cell xNeighbor = this.getCell().getNeighbor(direction.xComponent());

        if (yNeighbor.isBlocking() && xNeighbor.isBlocking())
            direction = direction.reversed();
        else if (xNeighbor.isBlocking())
            direction = direction.xFlipped();
        else if (yNeighbor.isBlocking())
            direction = direction.yFlipped();
        else if (defaultCell.isBlocking())
            direction = direction.reversed();
    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.BONE);
        bats.removeIf(bat -> bat == this);
    }

    public static void addBat(Bat bat) {
        bats.add(bat);
    }

    public static List<Bat> getBats() {
        return bats;
    }

    @Override
    public String getTileName() {
        return "bat";
    }

}
