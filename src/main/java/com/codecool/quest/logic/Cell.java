package com.codecool.quest.logic;

import com.codecool.quest.logic.mapentities.MapEntity;
import com.codecool.quest.logic.util.Direction;

public class Cell implements Drawable {
    private CellType type;
    private MapEntity mapEntity;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean isObstacle() {
        return type.isObstacle();
    }

    public MapEntity getMapEntity() {
        return mapEntity;
    }

    public void setMapEntity(MapEntity mapEntity) {
        this.mapEntity = mapEntity;
    }

    public boolean hasMapEntity() {
        return mapEntity != null;
    }

    public Cell getNeighbour(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getNeighbour(Direction direction) {
        return getNeighbour(direction.getDx(), direction.getDy());
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
