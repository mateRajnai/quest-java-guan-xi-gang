package com.codecool.quest.logic;

import com.codecool.quest.logic.items.Item;
import javafx.scene.control.ListView;

public class Inventory{
    private ListView<Item> element = new ListView<>();

    public Inventory() {
        element.setPrefWidth(30);
        element.setPrefHeight(70);
    }

    public void add(Item item) {
        element.getItems().add(item);
        item.removeFromCell();
    }

    public ListView<Item> asListView() {
        return element;
    }
}