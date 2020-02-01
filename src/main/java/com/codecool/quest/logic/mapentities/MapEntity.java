package com.codecool.quest.logic.mapentities;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.GameMap;

public abstract class MapEntity implements Drawable {
    protected Cell cell;
    protected GameMap map;

    public MapEntity(Cell cell) {
        this.cell = cell;
        this.map = cell.getGameMap();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) { this.cell = cell;}

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
