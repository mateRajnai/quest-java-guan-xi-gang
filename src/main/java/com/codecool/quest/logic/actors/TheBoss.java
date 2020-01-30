package com.codecool.quest.logic.actors;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.HandleAttack;
import java.util.ArrayList;
import java.util.List;

public class TheBoss extends Actor {

    HandleAttack handleAttack = new HandleAttack();
    private static final int INITIAL_HEALTH = 60;
    private static final int INITIAL_ATTACK_DAMAGE = 7;
    private static final int INITIAL_ARMOR = 0;
    private int coordinateSwitcher = -1;
    private static List<TheBoss> theBosses = new ArrayList<>();
    private static boolean isTheBossKilled = false;
    public TheBoss(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }
    public static boolean getIsTheBossKilled() {
        return isTheBossKilled;
    }

    public static void setIsTheBossKilled(boolean isTheBossKilled) {
        TheBoss.isTheBossKilled = isTheBossKilled;
    }

    public static void add(TheBoss theBoss) {
        theBosses.add(theBoss);
    }
    public static List<TheBoss> getTheBosses() {
        return theBosses;
    }
    public void move() {
        int dx = coordinateSwitcher;
        int dy = 0;
        Cell nextCell = super.getCell().getNeighbor(dx, dy);
        if (fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {
            coordinateSwitcher *= -1;
            dx = coordinateSwitcher;
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
    @Override
    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
        theBosses.removeIf(theBoss -> theBoss == this);
        isTheBossKilled = true;
    }
    @Override
    public String getTileName() {
        return "the boss";
    }

}