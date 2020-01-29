package com.codecool.quest.layers;

import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.Inventory;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.util.Optional;

public class UI {

    private GameMap map;

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

    public UI(Layout layout, Screen screen) {
        SidePanel sidePanel = layout.getSidePanel();
        this.map = screen.getMap();
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
                if (selectedItem instanceof Key && map.getPlayer().isDoorInNeighbourCell()) {
                    map.getPlayer().openDoorInNeighbourCell();
                    int indexOfKey = inventory.getSelectionModel().getSelectedIndex();
                    inventory.getItems().remove(indexOfKey);
                    screen.focusLayout();
                }
            }
        });
    }

    public void initPickUpButton() {
        pickUpButton.setOnAction(actionEvent -> {
            addItemToInventory();
            screen.focusLayout();
        });
    }

    private void addItemToInventory() {
        Item item = map.getPlayer().getCell().getItem();
        if (item != null) inventory.add(item);
    }

    public void setCharacterName() {
        Optional<String> result = characterNameDialog.showAndWait();
        result.ifPresent(name -> characterName.setText(name));
    }

    public void showEndingAlert() {
        endingAlert.showAndWait();
    }
}
