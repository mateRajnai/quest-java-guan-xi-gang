package com.codecool.quest.logic;

import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.ItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Inventory extends TableView<ItemData> {

    private ObservableList<ItemData> inv = FXCollections.observableArrayList();
    TableColumn<ItemData, String> itemCol;
    TableColumn<ItemData, Integer> amountCol;

    public Inventory() {

        this.setFixedCellSize(25);
        this.setPrefHeight(150);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.setPlaceholder(new Label("No items in inventory"));

        itemCol = new TableColumn<>("Item");
        itemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemCol.setPrefWidth(100);

        amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setPrefWidth(100);

        this.setItems(inv);
        this.getColumns().add(itemCol);
        this.getColumns().add(amountCol);
    }

    private ItemData findItemData(Item item) {
        return findItemData(item.getTileName());
    }

    private ItemData findItemData(String tileName) {
        for (ItemData itemData : inv)
            if (itemData.getName().equals(tileName))
                return itemData;
        return null;
    }

    public void add(Item item) {
        ItemData itemData = findItemData(item);
        if (itemData == null)
            inv.add(new ItemData(item.getTileName()));
        else
            itemData.increaseAmount();
//        item.removeFromMap();
        this.refresh();
    }

    public boolean hasKey() {
        return findItemData("key") != null;
    }

    public void removeByTileName(String tileName) {
        ItemData itemData = findItemData(tileName);
        if (itemData != null)
            itemData.decreaseAmount();
        this.refresh();
    }

    public void clear() {
        inv.clear();
        this.refresh();
    }
}