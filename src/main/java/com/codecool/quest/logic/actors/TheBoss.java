package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;

public class TheBoss extends Actor {

    private static final int INITIAL_HEALTH = 60;
    private static final int INITIAL_ATTACK_DAMAGE = 7;
    private static final int INITIAL_ARMOR = 0;

    private static TheBoss theBoss;
    private static boolean isDefeated = false;

    private Direction direction = Direction.LEFT;

    public TheBoss(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        theBoss = this;
        setDefeated(false);
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

    public static boolean isDefeated() {
        return isDefeated;
    }

    public static void setDefeated(boolean isDefeated) {
        TheBoss.isDefeated = isDefeated;
    }

    @Override
    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
        theBoss = null;
        setDefeated(true);
    }

    public static void reset() {
        setDefeated(false);
    }

    @Override
    public String getTileName() {
        return "the boss";
    }

}