package com.codecool.quest.logic.util;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class CellSurroundings {

    private static class Edges {
        private int topRow, bottomRow, leftCol, rightCol;

        private Edges(Cell cell, int boxSize) {
            int y = cell.getY(),
                    x = cell.getX();
            int mapHeight = cell.getGameMap().getHeight(),
                    mapWidth = cell.getGameMap().getWidth();

            topRow = y - boxSize / 2;
            if (topRow < 0) topRow = 0;

            bottomRow = y + boxSize / 2;
            if (bottomRow > mapHeight - 1) bottomRow = mapHeight - 1;

            leftCol = x - boxSize / 2;
            if (leftCol < 0) leftCol = 0;

            rightCol = x + boxSize / 2;
            if (rightCol > mapWidth - 1) rightCol = mapWidth - 1;
        }
    }

    public static List<Cell> collect(Cell cell, int boxSize) {
        boxSize = boxSize + (boxSize % 2 - 1);
        List<Cell> surroundings = new ArrayList<>((int) (Math.pow(boxSize, 2) - 1));

        Edges edges = new Edges(cell, boxSize);

        Cell surroundingCell;
        for (int y = edges.topRow; y <= edges.bottomRow; y++) {
            for (int x = edges.leftCol; x <= edges.rightCol; x++) {
                surroundingCell = cell.getGameMap().getCell(x, y);
                if (surroundingCell != cell) {
                    surroundings.add(surroundingCell);
                }
            }
        }
        return surroundings;
    }
}
