package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

     private class ActorEnvironmentCheck {

        int[] horizontalDirections = getDirectionDistancesBasedOnPlayer("X");
        int[] verticalDirections = getDirectionDistancesBasedOnPlayer("Y");

        //map will contain "horizontalDirectionIndex" and "verticalDirectionIndex" keys
        Map<String, Integer> shortestDirectionIndexes = getShortestDirectionIndexes(horizontalDirections, verticalDirections);;

         //map will contain "nextHorizontalMoveCell" and "nextVerticalMoveCell" keys
         Map<String, Cell> directionOfPlayer = getDirectionOfPlayer(shortestDirectionIndexes);


         int closestVerticalDirection = horizontalDirections[shortestDirectionIndexes.get("horizontalDirectionIndex")];
         int closestHorizontalDirection = verticalDirections[shortestDirectionIndexes.get("verticalDirectionIndex")];

         Cell closestHorizontalCell = directionOfPlayer.get("nextHorizontalMoveCell");
         Cell closestVerticalCell = directionOfPlayer.get("nextVerticalMoveCell");

         public boolean isActorCloserHorizontalToTarget () {

             System.out.println(Arrays.toString(horizontalDirections) + "\n");
             System.out.println(Arrays.toString(verticalDirections) + "\n");

             System.out.println("vertical" + closestVerticalCell.getX() + " y " + closestVerticalCell.getY());
             System.out.println("horizontal" + closestHorizontalCell.getX() + " y " + closestHorizontalCell.getY());
             return (closestHorizontalDirection <= closestVerticalDirection &&
                     closestHorizontalDirection != -1 &&
                     !closestHorizontalCell.isBlocking()) ||
             !(closestHorizontalDirection <= closestVerticalDirection) &&
                     closestHorizontalDirection != -1 &&
                     closestVerticalCell.isBlocking();
         }

         public boolean isActorCloserVerticalToTarget () {
             return (closestVerticalDirection <= closestHorizontalDirection &&
                     closestVerticalDirection != -1 &&
                     !closestVerticalCell.isBlocking()) ||
                     !(closestVerticalDirection <= closestHorizontalDirection) &&
                             closestVerticalDirection != -1 &&
                             closestHorizontalCell.isBlocking();
         }

         public boolean isTargetReachedHorizontally () {
             return closestHorizontalDirection == -1 &&
                     closestVerticalDirection == 0 &&
                     !closestVerticalCell.isBlocking();
         }

         public boolean isTargetReachedVertically () {
             return closestHorizontalDirection == 0 &&
                     closestVerticalDirection == -1 &&
                     !closestHorizontalCell.isBlocking();
         }

         public boolean isTargetHorizontalLevelReached () {
             return closestHorizontalDirection == -1 &&
                     !closestVerticalCell.isBlocking();
         }

         public boolean isTargetVerticalLevelReached () {
             return closestVerticalDirection == -1 &&
                     !closestHorizontalCell.isBlocking();
         }

         public Cell getClosestHorizontalCell() {
             return closestHorizontalCell;
         }

         public Cell getClosestVerticalCell() {
             return closestVerticalCell;
         }
     }

    public Cell pathFinding() {

        ActorEnvironmentCheck actorEnvironmentCheck  = new ActorEnvironmentCheck();

        Cell closestCellToPlayer = attacker.getCell();

        //we have set the right direction towards the player
        //now we decide from which one is the shortest way to the player from horizontal move and vertical move

        //if we are closer with horizontal move then move horizontally
        if(actorEnvironmentCheck.isActorCloserHorizontalToTarget()) {
            closestCellToPlayer = actorEnvironmentCheck.getClosestHorizontalCell();
            System.out.println("1");

            //else if we are closer with vertical move then move vertically
        } else if(actorEnvironmentCheck.isActorCloserVerticalToTarget()) {
            closestCellToPlayer = actorEnvironmentCheck.getClosestVerticalCell();
            System.out.println("2");

            // else if we are horizontally reached the player (that means we are next to it horizontally)
        } else if(actorEnvironmentCheck.isTargetReachedHorizontally() &&
                !actorEnvironmentCheck.isActorCloserVerticalToTarget()) {
            closestCellToPlayer = (!actorEnvironmentCheck.getClosestHorizontalCell().isBlocking()) ? actorEnvironmentCheck.getClosestHorizontalCell() : actorEnvironmentCheck.getClosestVerticalCell();
            System.out.println("3");

            // else if we are vertically reached the player (that means we are next to it vertically)
        } else if(actorEnvironmentCheck.isTargetReachedVertically() &&
                !actorEnvironmentCheck.isActorCloserHorizontalToTarget()) {
            closestCellToPlayer = (!actorEnvironmentCheck.getClosestVerticalCell().isBlocking()) ? actorEnvironmentCheck.getClosestVerticalCell() : actorEnvironmentCheck.getClosestHorizontalCell();
            System.out.println("4");

            // else if we are horizontally reached the players level, then we will move vertically
        } else if(actorEnvironmentCheck.isTargetHorizontalLevelReached()) {
            closestCellToPlayer = actorEnvironmentCheck.getClosestVerticalCell();
            System.out.println("5");

            // else if we are vertically reached the players level, then we will move horizontally
        } else if(actorEnvironmentCheck.isTargetVerticalLevelReached()) {
            closestCellToPlayer = actorEnvironmentCheck.getClosestHorizontalCell();
            System.out.println("6");
        }

        return closestCellToPlayer;

    }

    private int[] getDirectionDistancesBasedOnPlayer(String orientation) {

        int[] directions = new int[2];

        int playerCurrentXPosition = attacker.getPlayerCurrentPosition().getX();
        int playerCurrentYPosition = attacker.getPlayerCurrentPosition().getY();
        int attackerCurrentXPosition = attacker.getX();
        int attackerCurrentYPosition = attacker.getY();

        switch(orientation) {
            case "X":
                //left
                directions[0] = Math.abs((attackerCurrentXPosition - 1) - playerCurrentXPosition);
                //right
                directions[1] = Math.abs((attackerCurrentXPosition + 1) - playerCurrentXPosition);
                break;
            case "Y":
                //up
                directions[0] = Math.abs((attackerCurrentYPosition - 1) - playerCurrentYPosition);
                //down
                directions[1] = Math.abs((attackerCurrentYPosition + 1) - playerCurrentYPosition);
        }
        return directions;
    }

    private Map< String, Integer> getShortestDirectionIndexes(int[] horizontalDirections, int[] verticalDirections) {

        int closestNumber = monsterAttackRange * 2;
        Map<String, Integer> shortestDirectionIndexes = new HashMap<>();
        System.out.println(closestNumber + " " + Arrays.toString(horizontalDirections) + " " + Arrays.toString(verticalDirections));

        for (int indexUpDown = 0; indexUpDown < verticalDirections.length; indexUpDown++) {
            for (int indexRightLeft = 0; indexRightLeft < horizontalDirections.length; indexRightLeft++) {
                if (closestNumber > (verticalDirections[indexUpDown] + horizontalDirections[indexRightLeft])) {
                    closestNumber = (verticalDirections[indexUpDown] + horizontalDirections[indexRightLeft]);
                    shortestDirectionIndexes.put("horizontalDirectionIndex", indexRightLeft);
                    shortestDirectionIndexes.put("verticalDirectionIndex", indexUpDown);
                }
            }
        }
        return shortestDirectionIndexes;
    }


    private Map< String,Cell> getDirectionOfPlayer(Map< String, Integer> shortestDirectionIndexes) {

        Map<String, Cell> directionOfPlayer = new HashMap<>();
        String[] directionNames  = new String[] {"left", "right", "up", "down"};

        String horizontalMoveDirection = directionNames[shortestDirectionIndexes.get("horizontalDirectionIndex")];
        String verticalMoveDirection = directionNames[shortestDirectionIndexes.get("verticalDirectionIndex") + 2];

        directionOfPlayer.put("nextHorizontalMoveCell", getDirectionOfClosestCellToPlayer(horizontalMoveDirection));
        directionOfPlayer.put("nextVerticalMoveCell", getDirectionOfClosestCellToPlayer(verticalMoveDirection));

        return directionOfPlayer;
    }


    private Cell getDirectionOfClosestCellToPlayer(String finalDirection) {
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
