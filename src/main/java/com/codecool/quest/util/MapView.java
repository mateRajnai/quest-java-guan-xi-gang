package com.codecool.quest.util;

import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.actors.Player;

public class MapView {
    private final static int CANVAS_WIDTH = 25;
    private final static int CANVAS_HEIGHT = 20;
    private final static int WIDTH_BREAKPOINT = (int) (CANVAS_WIDTH * 0.66);
    private final static int HEIGHT_BREAKPOINT = (int) (CANVAS_HEIGHT * 0.66);

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
        if (x < CANVAS_WIDTH / 2)
            return 0;
        else if (x < mapWidth - CANVAS_WIDTH / 2)
            return x - CANVAS_WIDTH / 2;
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
        if (minX == 0 && x < WIDTH_BREAKPOINT)
            return minX;

        int relativeX = x - this.minX;
        if (relativeX < CANVAS_WIDTH - WIDTH_BREAKPOINT)
            return --minX;
        else if (relativeX < WIDTH_BREAKPOINT)
            return minX;
        else if (x < mapWidth - (CANVAS_WIDTH - WIDTH_BREAKPOINT))
            return ++minX;
        else
            return minX;
    }

    public int minY() {
        int y = player.getY();
        if (minY == 0 && y < HEIGHT_BREAKPOINT)
            return minY;

        int relativeY = y - this.minY;
        if (relativeY < CANVAS_HEIGHT - HEIGHT_BREAKPOINT)
            return --minY;
        else if (relativeY < HEIGHT_BREAKPOINT)
            return minY;
        else if (y < mapHeight - (CANVAS_HEIGHT - HEIGHT_BREAKPOINT))
            return ++minY;
        else
            return minY;
    }
}
