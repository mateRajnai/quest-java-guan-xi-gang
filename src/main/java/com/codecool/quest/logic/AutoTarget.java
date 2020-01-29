package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;

import java.util.Arrays;

public class AutoTarget {

    private int monsterAttackRange;
    private Actor attacker;

    public AutoTarget(int monsterAttackRange, Actor attacker) {
        this.monsterAttackRange = monsterAttackRange;
        this.attacker = attacker;
    }

    public AutoTarget(Actor attacker){
        this.attacker = attacker;
    }

    public Cell getClosestCellToPlayer() {

        int playerCurrentXPosition = attacker.getPlayerCurrentPosition().getX();
        int playerCurrentYPosition = attacker.getPlayerCurrentPosition().getY();
        int attackerCurrentXPosition = attacker.getX();
        int attackerCurrentYPosition = attacker.getY();

        // i will add +2 to the "upDownDirections"'s index because that is the way how it will have the correct direction
        // from the "directionNames"
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

        Cell nextHorizontalMoveCell = getDirectionOfClosestCell(directionNames[horizontalIndex]);
        Cell nextVerticalMoveCell = getDirectionOfClosestCell(directionNames[verticalIndex + 2]);

        //we have set the right direction towards the player
        //now we decide from which one is the shortest way to the player from horizontal move and vertical move

        //if we are closer with horizontal move then:
        if(leftRightDirections[horizontalIndex] <= upDownDirections[verticalIndex] &&
                leftRightDirections[horizontalIndex] != 0 &&
                !nextHorizontalMoveCell.isBlocking()) {
            closestCellToPlayer = nextHorizontalMoveCell;

            //else if we are closer with vertical move then:
        } else if(upDownDirections[verticalIndex] < leftRightDirections[horizontalIndex] &&
                upDownDirections[verticalIndex] != 0 &&
                !nextVerticalMoveCell.isBlocking()) {
            closestCellToPlayer = nextVerticalMoveCell;

            // else if we are horizontally reached the player (that means we are next to it horizontally)
        } else if(leftRightDirections[horizontalIndex] == 0 &&
                upDownDirections[verticalIndex] == 1 &&
                !nextVerticalMoveCell.isBlocking()) {
            closestCellToPlayer = nextVerticalMoveCell;

            // else if we are vertically reached the player (that means we are next to it vertically)
        } else if(leftRightDirections[horizontalIndex] == 1 &&
                upDownDirections[verticalIndex] == 0 &&
                !nextHorizontalMoveCell.isBlocking()) {
            closestCellToPlayer = nextHorizontalMoveCell;

            // else if we are horizontally reached the players level, then we will move vertically
        } else if(leftRightDirections[horizontalIndex] == 0 &&
                !nextVerticalMoveCell.isBlocking()) {
            closestCellToPlayer = nextVerticalMoveCell;

            // else if we are vertically reached the players level, then we will move horizontally
        } else if(upDownDirections[verticalIndex] == 0 &&
                !nextHorizontalMoveCell.isBlocking()) {
            closestCellToPlayer = nextHorizontalMoveCell;
        }

        System.out.println("lowest difference rightleft" + horizontalIndex + " updown " + verticalIndex);
        System.out.println("player    x " + playerCurrentXPosition + "y " + playerCurrentYPosition);
        System.out.println(" rightleft " + Arrays.toString(leftRightDirections) + " updown " + Arrays.toString(upDownDirections));
        //System.out.println("x to player " + closestCellToPlayer.getX() + " y to player " + closestCellToPlayer.getY());
        System.out.println("x enemy " + attacker.getX() + "y enemy " + attacker.getY());
        return closestCellToPlayer;
    }

    private Cell getDirectionOfClosestCell(String finalDirection) {
        //search for the closest direction and returns the cell

        System.out.println(finalDirection);
        switch(finalDirection) {
            case "left":
                return attacker.getCell().getNeighbor(-1 ,0);
            case "right":
                return attacker.getCell().getNeighbor(1 ,0);
            case "up":
                return attacker.getCell().getNeighbor(0, -1);
            case "down":
                return attacker.getCell().getNeighbor(0, 1);

            default:
                throw new IllegalStateException("Unexpected value: " + finalDirection);
        }
    }

    public boolean isPlayerNexToIt() {
        int[] xDirections = {1, 0, -1, 0};
        int[] yDirections = {0, 1, 0, -1};

        for (int index = 0; index < xDirections.length; index++) {
            Cell nextCell = attacker.getCell().getNeighbor(xDirections[index], yDirections[index]);

            if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("player")) {
                return true;
            }
        }
        return false;
    }
}
