package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;

public class TheBoss extends Actor {

    private static final int INITIAL_HEALTH = 60;
    private static final int INITIAL_ATTACK_DAMAGE = 7;
    private static final int INITIAL_ARMOR = 0;

    private static TheBoss theBoss;

    private Direction direction = Direction.LEFT;

    public TheBoss(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        theBoss = this;
    }

    public static TheBoss getTheBoss() {
        return theBoss;
    }

    public void move() {
        if (isTheBossInSlumber()) return;

        Cell nextCell = this.getCell().getNeighbor(direction);

        if (!nextCell.getType().isTraversable() || nextCell.hasFixedActor()) {
            direction = direction.xFlipped();
            nextCell = super.getCell().getNeighbor(direction);
        }

        if (!nextCell.isBlocking())
            moveTo(nextCell);
        else if (nextCell.getActor() instanceof Player)
            this.attack(nextCell.getActor());
    }

    public static boolean isTheBossInSlumber() {
        return theBoss == null;
    }

    public static boolean isTheBossKilled() {
        return theBoss.health <= 0;
    }

    @Override
    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
        theBoss = null;
    }

    @Override
    public String getTileName() {
        return "the boss";
    }

}