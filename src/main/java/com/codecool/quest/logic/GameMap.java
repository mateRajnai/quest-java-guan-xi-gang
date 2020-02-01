package com.codecool.quest.logic;

import com.codecool.quest.logic.mapentities.*;
import com.codecool.quest.logic.mapentities.actors.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Automaton> getAutomatons() {
        List<Automaton> automatons = new ArrayList<>();
        for (Cell[] col : cells)
            for (Cell cell : col)
                if (cell.getMapEntity() instanceof Automaton)
                    automatons.add((Automaton) cell.getMapEntity());
        return automatons;
    }
}
