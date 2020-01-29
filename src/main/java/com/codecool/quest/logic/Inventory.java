package com.codecool.quest.logic;

import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
import javafx.scene.control.ListView;

public class Inventory extends ListView<Item> {

    public Inventory() {
        this.setPrefWidth(30);
        this.setPrefHeight(70);
    }

    public void add(Item item) {
        this.getItems().add(item);
        item.removeFromMap();
    }

    public boolean hasKey() {
        for (Item item : this.getItems())
            if (item instanceof Key)
                return true;
        return false;
    }

    public void removeByTileName(String tileName) {
        this.getItems().removeIf(item -> tileName.equals(item.getTileName()));
    }

    public void removeItem(Item item) {
        this.getItems().remove(item);
    }
}