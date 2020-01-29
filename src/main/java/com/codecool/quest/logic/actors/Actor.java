package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;
    AutoTarget autotarget = new AutoTarget(this.monsterAttackRange, this);

    protected int health;
    protected int attackDamage;
    protected int armor;
    protected List<String> fixTiles = new ArrayList<>(Arrays.asList("wall", "bronze torch", "campfire", "chest open"));
    protected List<String> fixActors = new ArrayList<>(Arrays.asList("pot", "chest open", "chest closed"));
    protected static Cell playerCurrentPosition;
    protected int monsterAttackRange = 3;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {}

    protected void moveTo(Cell nextCell) {
        this.cell.setActor(null);
        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    public int getMonsterAttackRange() {
        return monsterAttackRange;
    }

    public void setMonsterAttackRange(int monsterAttackRange) {
        this.monsterAttackRange = monsterAttackRange;
    }

    public int attack(int targetHealth) {
        return targetHealth - this.attackDamage;
    }

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int newHealth) {this.health = newHealth;}

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(int newAttackDamage) {this.attackDamage = newAttackDamage;}

    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int newArmor) {this.armor = newArmor;}

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        cell.setActor(this);
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setPlayerCurrentPosition(Cell playerCurrentPosition) {
        Actor.playerCurrentPosition = playerCurrentPosition;
    }

    public Cell getPlayerCurrentPosition() {
        return playerCurrentPosition;
    }

    public Cell getClosestCellToPlayer() {

        int playerCurrentXPosition = playerCurrentPosition.getX();
        int playerCurrentYPosition = playerCurrentPosition.getY();
        int attackerCurrentXPosition = this.getCell().getX();
        int attackerCurrentYPosition = this.getCell().getY();

        int[] upDownDirections = new int[2];
        int[] leftRightDirections = new int[2];
        String[] directionNames  = new String[] {"left", "right", "up", "down"};
        int closestNumber = monsterAttackRange * 2;
        int horizontalIndex = 0;
        int verticalIndex = 0;

        Cell closestCellToPlayer = null;

        //left
        leftRightDirections[0] = Math.abs((attackerCurrentXPosition - 1) - playerCurrentXPosition);
        //right
        leftRightDirections[1] = Math.abs((attackerCurrentXPosition + 1) - playerCurrentXPosition);

        //up
        upDownDirections[0] = Math.abs((attackerCurrentYPosition - 1) - playerCurrentYPosition);
        //down
        upDownDirections[1] = Math.abs((attackerCurrentYPosition + 1) - playerCurrentYPosition);


        //get the lowest distance from the lists
        for(int indexUpDown = 0; indexUpDown < upDownDirections.length; indexUpDown++) {
            for(int indexRightLeft = 0; indexRightLeft < leftRightDirections.length; indexRightLeft++) {
                if (closestNumber > (upDownDirections[indexUpDown] + leftRightDirections[indexRightLeft])) {
                    closestNumber = (upDownDirections[indexUpDown] + leftRightDirections[indexRightLeft]);
                    horizontalIndex = indexRightLeft;
                    verticalIndex = indexUpDown;
                }
            }
        }

        if(leftRightDirections[horizontalIndex] <= upDownDirections[verticalIndex] &&
                leftRightDirections[horizontalIndex] != 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directionNames[horizontalIndex]);
        } else if(upDownDirections[verticalIndex] < leftRightDirections[horizontalIndex] &&
                upDownDirections[verticalIndex] != 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directionNames[verticalIndex + 2]);
        } else if(leftRightDirections[horizontalIndex] == 0 && upDownDirections[verticalIndex] == 1) {
            closestCellToPlayer = getDirectionOfClosestCell(directionNames[verticalIndex + 2]);
        } else if(leftRightDirections[horizontalIndex] == 1 && upDownDirections[verticalIndex] == 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directionNames[horizontalIndex]);
        } else if(leftRightDirections[horizontalIndex] == 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directionNames[verticalIndex + 2]);
        } else if(upDownDirections[verticalIndex] == 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directionNames[horizontalIndex]);
        }

        System.out.println("lowest difference rightleft" + horizontalIndex + " updown " + verticalIndex);
        System.out.println("player    x " + playerCurrentPosition.getX() + "y " + playerCurrentPosition.getY());
        System.out.println(" rightleft " + Arrays.toString(leftRightDirections) + " updown " + Arrays.toString(upDownDirections));
        //System.out.println("x to player " + closestCellToPlayer.getX() + " y to player " + closestCellToPlayer.getY());
        System.out.println("x enemy " + this.getX() + "y enemy " + this.getY());
        return closestCellToPlayer;
    }

    private Cell getDirectionOfClosestCell(String finalDirection) {
        //search for the closest direction and returns the cell

        System.out.println(finalDirection);
        switch(finalDirection) {
            case "left":
                return this.getCell().getNeighbor(-1 ,0);
            case "right":
                return this.getCell().getNeighbor(1 ,0);
            case "up":
                return this.getCell().getNeighbor(0, -1);
            case "down":
                return this.getCell().getNeighbor(0, 1);

            default:
                throw new IllegalStateException("Unexpected value: " + finalDirection);
        }
    }

    public boolean isPlayerNexToIt() {
        int[] xDirections = {1, 0, -1, 0};
        int[] yDirections = {0, 1, 0, -1};

        for (int index = 0; index < xDirections.length; index++) {
            Cell nextCell = this.getCell().getNeighbor(xDirections[index], yDirections[index]);

            if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("player")) {
                return true;
            }
        }
        return false;
    }

    protected void attack(Actor target) {
        target.setHealth(target.getHealth() - this.getAttackDamage());
        if(isDead(target)) {
            target.terminate();
        }
    }

    private boolean isDead(Actor target) {
        return target.getHealth() <= 0;
    }

    public abstract void terminate();

    public boolean isPlayerNear() {
        return Math.abs(this.getX() - playerCurrentPosition.getX()) <= monsterAttackRange &&
                Math.abs(this.getY() - playerCurrentPosition.getY()) <= monsterAttackRange;
    }



}
