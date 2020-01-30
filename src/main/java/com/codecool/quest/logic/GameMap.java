package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private List<Actor> actors = new ArrayList<>();

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

    public void add(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getExitCell() {
        for (Cell[] column: cells) {
            for (Cell cell: column)
                if (cell.getType() == CellType.DOWNSTAIRS)
                    return cell;
        }
        return null;
    }

}
