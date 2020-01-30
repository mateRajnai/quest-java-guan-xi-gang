package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

import java.util.List;

public abstract class Item implements Drawable {
    private Cell cell;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Cell getCell() { return cell; }

    public void setCell(Cell cell) { this.cell = cell; }

    public abstract List<Item> getInstances();

    public void removeFromMap() {
        this.cell.setItem(null);
        getInstances().removeIf(instance -> instance == this);
    }

    @Override
    public String toString() {
        return this.getTileName();
    }
}
