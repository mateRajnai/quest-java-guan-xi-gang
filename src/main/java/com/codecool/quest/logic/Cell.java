package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.util.Direction;

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

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean hasObstacle() {
        return type.isObstacle();
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public boolean hasActor() {
        return actor != null;
    }

    public void setItem(Item item) { this.item = item; }

    public Item getItem() { return item; }

    public Cell getNeighbour(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getNeighbour(Direction direction) {
        return getNeighbour(x + direction.getDx(), y + direction.getDy());
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
