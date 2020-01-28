package com.codecool.quest.logic;

import com.codecool.quest.logic.items.Item;
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
}