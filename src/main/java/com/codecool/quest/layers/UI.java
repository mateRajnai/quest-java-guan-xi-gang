package com.codecool.quest.layers;

import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.Inventory;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.util.Optional;

public class UI {

    private GameMap map;
    private Player player;

    private Label characterName;
    private Inventory inventory;
    private Button pickUpButton;

    private Screen screen;

    public UI(Screen screen) {
        SidePanel sidePanel = screen.getSidePanel();
        this.map = screen.getMap();
        this.player = this.map.getPlayer();
        this.characterName = sidePanel.getCharacterName();
        this.inventory = sidePanel.getInventory();
        this.pickUpButton = sidePanel.getPickUpButton();
        this.screen = screen;
        initInventory();
        initPickUpButton();
    }

    public void updateMap(GameMap map) {
        this.map = map;
        this.player = this.map.getPlayer();
    }

    public void setCharacterName() {
        Optional<String> result = MessageLoader.askForCharacterName();
        result.ifPresent(name -> characterName.setText(name));
    }

    public void initInventory() {
        inventory.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                Item selectedItem = inventory.getSelectionModel().getSelectedItem();
                if (selectedItem instanceof Key && player.isDoorInNeighbourCell()) {
                    openDoor();
                    screen.focusLayout();
                }
            }
        });
    }

    public void initPickUpButton() {
        pickUpButton.setOnAction(actionEvent -> {
            if (player.getCell().hasItem())
                addItemToInventory();
            screen.focusLayout();
        });
    }

    public void interact() {
        if (player.getCell().hasItem())
            addItemToInventory();
        else if (player.isDoorInNeighbourCell() && inventory.hasKey())
            openDoor();
    }

    public void addItemToInventory() {
        Item item = player.getCell().getItem();
        inventory.add(item);
    }

    public void openDoor() {
        player.openDoorInNeighbourCell();
        inventory.removeByTileName("key");
    }

    public void clearInventory() {
        inventory.clear();
    }
}
