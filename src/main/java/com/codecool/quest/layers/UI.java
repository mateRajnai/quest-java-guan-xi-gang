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

    private static TextInputDialog characterNameDialog;
    private static Alert endingAlert;

    static {
        initCharacterNameDialog();
        initEndingAlert();
    }

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

    private static void initCharacterNameDialog() {
        characterNameDialog = new TextInputDialog("hackerman");
        characterNameDialog.setTitle("Character setup");
        characterNameDialog.setHeaderText("Choose an epic name for your character!");
        characterNameDialog.setContentText("Epic name:");
        characterNameDialog.setGraphic(null);

        Button cancelButton = (Button) characterNameDialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setVisible(false);

        TextField nameInputField = characterNameDialog.getEditor();
        Button OKButton = (Button) characterNameDialog.getDialogPane().lookupButton(ButtonType.OK);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) -> {
            OKButton.setDisable(!(newValue.length() > 0 && newValue.length() <= 9));
        });
    }

    private static void initEndingAlert() {
        endingAlert = new Alert(
                Alert.AlertType.INFORMATION,
                "You have reached the stairs of wisdom!",
                ButtonType.OK
        );
        endingAlert.setGraphic(null);
        endingAlert.setHeaderText(null);
        endingAlert.setTitle("Epic victory message");
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

    public void setCharacterName() {
        Optional<String> result = characterNameDialog.showAndWait();
        result.ifPresent(name -> characterName.setText(name));
    }

    public void showEndingAlert() {
        endingAlert.showAndWait();
    }

    public void interact() {
        if (player.getCell().hasItem())
            addItemToInventory();
        else if (player.isDoorInNeighbourCell() && inventory.hasKey())
            openDoor();
    }

    public void addItemToInventory() {
        Item item = map.getPlayer().getCell().getItem();
        inventory.add(item);
    }

    public void openDoor() {
        player.openDoorInNeighbourCell();
        inventory.removeByTileName("key");
    }
}
