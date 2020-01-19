package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public void setCell(Cell cell) { this.cell = cell; }

    public Cell getCell() { return cell; }

    public void removeFromCell() {
        this.cell.setItem(null);
    }

    @Override
    public String toString() {
        return this.getTileName();
    }
}