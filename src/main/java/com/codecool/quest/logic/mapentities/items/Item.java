package com.codecool.quest.logic.mapentities.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.mapentities.Interactable;
import com.codecool.quest.logic.mapentities.MapEntity;

public abstract class Item extends MapEntity implements Drawable, Interactable {

    public Item(Cell cell) {
        super(cell);
        cell.setItem(this);
    }

    public void addToInventory() {
        map.getPlayer().acquire(this);
        this.cell.setItem(null);
        this.setCell(null);
    }

    @Override
    public void react() {
        addToInventory();
    }
}
