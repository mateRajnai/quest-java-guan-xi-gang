package com.codecool.quest.util;

import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.actors.Player;

public class MapView {
    private final static int CANVAS_WIDTH = 25;
    private final static int CANVAS_HEIGHT = 20;
    private final static int X_OFFSET = 12;
    private final static int Y_OFFSET = 9;

    private int mapWidth;
    private int mapHeight;

    private Player player;

    private int minX;
    private int minY;

    public MapView(GameMap map) {
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
        this.player = map.getPlayer();
        this.minX = initialMinX();
        this.minY = initialMinY();
    }

    private int initialMinX() {
        int x = player.getX();
        if (x <= X_OFFSET)
            return 0;
        else if (x < mapWidth - CANVAS_WIDTH + X_OFFSET)
            return x - X_OFFSET;
        else
            return mapWidth - CANVAS_WIDTH;
    }

    private int initialMinY() {
        int y = player.getY();
        if (y < CANVAS_HEIGHT / 2)
            return 0;
        else if (y < mapHeight - CANVAS_HEIGHT / 2)
            return y - CANVAS_HEIGHT / 2;
        else
            return mapHeight - CANVAS_HEIGHT;
    }

    public int minX() {
        int x = player.getX();
        if ((
                minX == 0 && x <= X_OFFSET) || (
                minX == mapWidth - CANVAS_WIDTH && x - X_OFFSET >= minX) || (
                x - minX == X_OFFSET
        ))
            return minX;
        else if (x - minX > X_OFFSET)
            return ++minX;
        else
            return --minX;
    }

    public int minY() {
        int y = player.getY();
        if ((
                minY == 0 && y <= Y_OFFSET) || (
                minY == mapHeight - CANVAS_HEIGHT && y - Y_OFFSET >= minY) || (
                y - minY == Y_OFFSET
        ))
            return minY;
        else if (y - minY > Y_OFFSET)
            return ++minY;
        else
            return --minY;
    }
}
