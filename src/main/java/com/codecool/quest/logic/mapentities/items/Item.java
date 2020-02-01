package com.codecool.quest.logic.mapentities.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.mapentities.MapEntity;

public abstract class Item extends MapEntity implements Drawable {

    public Item(Cell cell) {
        super(cell);
        cell.setItem(this);
    }
}
