package com.codecool.quest.logic;

import com.codecool.quest.logic.mapentities.actors.Actor;
import com.codecool.quest.logic.mapentities.items.Item;
import com.codecool.quest.logic.util.CardinalDirection;
import com.codecool.quest.logic.util.Direction;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
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

    public boolean isTransparent() {
        return type.isTransparent();
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public boolean hasActor() {
        return actor != null;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean hasItem() {
        return item != null;
    }

    public Cell getNeighbour(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getNeighbour(Direction direction) {
        return getNeighbour(direction.getDx(), direction.getDy());
    }

    public Cell getNeighbour(CardinalDirection cardinalDirection) {
        return getNeighbour(cardinalDirection.get());
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

    public int distanceFrom(Cell cell) {
        return Math.abs(this.x - cell.x) + Math.abs(this.y - cell.y);
    }

    public Direction directionTo(Cell cell) {
        return new Direction(cell.x - this.x, cell.y - this.y);
    }
}
