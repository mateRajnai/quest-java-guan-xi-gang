package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;

public class TheBoss extends Actor {

    AutoTarget autotarget = new AutoTarget(this.BOSS_ATTACK_RANGE, this);

    private static final int INITIAL_HEALTH = 60;
    private static final int INITIAL_ATTACK_DAMAGE = 7;
    private static final int INITIAL_ARMOR = 0;

    private static TheBoss theBoss;
    private static boolean isDefeated = false;

    private Direction direction = Direction.LEFT;

    private static final int BOSS_ATTACK_RANGE = 6;
    private int coordinateSwitcher = -1;
    private int dx;
    private int dy;

    public TheBoss(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        theBoss = this;
        setDefeated(false);
        this.setMonsterAttackRange(BOSS_ATTACK_RANGE);
    }

    public static TheBoss getTheBoss() {
        return theBoss;
    }

    public void move() {
        Cell nextCell;


        //if player is near it will search for the next closest possible cell
        if(isPlayerNear()) {
            nextCell= autotarget.pathFinding();

            //step on nextCell if possible
            if (!nextCell.isBlocking() && !isPlayerNexToIt()) {
                this.moveTo(nextCell);
                //if there is a player, then attack
            } else if(isPlayerNexToIt()){
                Actor target = getPlayerCurrentPosition().getActor();
                this.attack(target);
                //if something blocking the player's side then just stand and wait
            } else if(!nextCell.isBlocking()){
                this.moveTo(this.getCell());
            }
        }
        //if player is not near it will do the standard movement
        else {
            dx = coordinateSwitcher;
            dy = 0;
            nextCell = this.getCell().getNeighbor(dx, dy);
            standardMovement(nextCell);
        }

    }

    private void standardMovement(Cell nextCell) {

        //bounce if the nexcell is an obstacle
        if (nextCell.isBlocking()) {
            coordinateSwitcher *= -1;
            dx = coordinateSwitcher;
            nextCell = super.getCell().getNeighbor(dx, dy);
            this.moveTo(nextCell);
        }
        else {
            this.moveTo(nextCell);
        }

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