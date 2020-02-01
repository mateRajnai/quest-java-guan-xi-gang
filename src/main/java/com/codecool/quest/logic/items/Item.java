package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.mapentities.MapEntity;

public abstract class Item extends MapEntity {
    public Item(Cell cell) {
        super(cell);
    }
}
