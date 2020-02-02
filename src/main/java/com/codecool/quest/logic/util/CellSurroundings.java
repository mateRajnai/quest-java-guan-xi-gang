package com.codecool.quest.logic.util;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class CellSurroundings {

    private final Cell cell;

    private List<Cell> surroundings;

    private static class Edges {
        private int topRow, bottomRow, leftCol, rightCol;
        private Edges(int x, int y, int boxSize, int mapWidth, int mapHeight) {
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

    public CellSurroundings(Cell cell, int boxSize) {
        this.cell = cell;
        int mapWidth = cell.getGameMap().getWidth();
        int mapHeight = cell.getGameMap().getHeight();
        boxSize = boxSize + (boxSize % 2 - 1);
        Edges edges = new Edges(cell.getX(), cell.getY(), boxSize, mapWidth, mapHeight);
        surroundings = new ArrayList<>((int) (Math.pow(boxSize, 2) - 1));
        collectSurroundingCells(edges);
    }

    private void collectSurroundingCells(Edges edges) {
        Cell surroundingCell;
        for (int y = edges.topRow; y <= edges.bottomRow; y++) {
            for (int x = edges.leftCol; x <= edges.rightCol; x++) {
                surroundingCell = cell.getGameMap().getCell(x, y);
                if (surroundingCell != cell && !surroundingCell.isObstacle()) {
                    surroundings.add(surroundingCell);
                }
            }
        }
    }

    public List<Cell> getCells() {
        return surroundings;
    }
}
