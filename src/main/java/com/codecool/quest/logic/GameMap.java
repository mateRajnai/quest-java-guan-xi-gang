package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Hammer;
import com.codecool.quest.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Skeleton> skeletons = new ArrayList<>();
    private Item item;

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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void addSkeleton(Skeleton skeleton) {
        this.skeletons.add(skeleton);
    }

    public List<Skeleton> getSkeletons() {
        return skeletons;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setItem(Item item) {this.item = item;}

    public void removeHammer() {
        // A player azért nem null, mert korábban a kódban már inicializálva van a setPlayer által. Tehát mire elér ide a game logic, addigra a setPlayer meg van hívva.
        int playerPositionX = player.getX();
        int playerPositionY = player.getY();
        if (hammer == null)
            System.out.println("hammer is null");
        int itemPositionX = hammer.getX();
        int itemPositionY = hammer.getY();

        if (playerPositionX == itemPositionX && playerPositionY == itemPositionY)
            hammer.getCell().setItem(null);
    }
}
