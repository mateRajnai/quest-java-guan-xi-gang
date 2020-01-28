package com.codecool.quest.util;

import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.actors.Player;

public class MapView {
    private final static int CANVAS_WIDTH = 25;
    private final static int CANVAS_HEIGHT = 20;
    private final static int WIDTH_BREAKPOINT = (int) (CANVAS_WIDTH * 0.75);
    private final static int DELTA_WIDTH = CANVAS_WIDTH / 2;
    private final static int HEIGHT_BREAKPOINT = (int) (CANVAS_HEIGHT * 0.75);
    private final static int DELTA_HEIGHT = CANVAS_HEIGHT / 2;

    private int mapWidth;
    private int mapHeight;

    private Player player;

    public MapView(GameMap map) {
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
        this.player = map.getPlayer();
    }

    private int getRelativeX() {
        int x = player.getX();
        if (x < WIDTH_BREAKPOINT)
            return x;
        else if (x < mapWidth - WIDTH_BREAKPOINT)
            return ((x - WIDTH_BREAKPOINT) % DELTA_WIDTH) + (CANVAS_WIDTH - WIDTH_BREAKPOINT);
        else
            return CANVAS_WIDTH - (mapWidth - x);
    }

    private int getRelativeY() {
        int y = player.getY();
        if (y < HEIGHT_BREAKPOINT)
            return y;
        else if (y < mapHeight - HEIGHT_BREAKPOINT)
            return ((y - HEIGHT_BREAKPOINT) % DELTA_HEIGHT) + (CANVAS_HEIGHT - HEIGHT_BREAKPOINT);
        else
            return CANVAS_HEIGHT - (mapHeight - y);
    }

    public int minX() {
        return player.getX() - getRelativeX();
    }

    public int minY() {
        return player.getY() - getRelativeY();
    }
}
