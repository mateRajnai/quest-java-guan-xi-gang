package com.codecool.quest.layers;

import com.codecool.quest.logic.Inventory;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class SidePanel extends GridPane {

    private Label healthLabel = new Label("Health: ");
    private Label characterNameLabel = new Label("Epic name: ");
    private Label healthPoints = new Label();
    private Label characterName = new Label("hackerman");
    private Inventory inventory = new Inventory();
    private Button pickUpButton = new Button("Pick up");

    public SidePanel() {
        ColumnConstraints col1 = new ColumnConstraints(85);
        this.getColumnConstraints().add(col1);
        this.setPrefWidth(200);
        this.setPadding(new Insets(10));
        this.add(characterNameLabel, 0, 1);
        this.add(characterName, 1, 1);
        this.add(healthLabel, 0, 0);
        this.add(healthPoints, 1, 0);
        this.add(inventory, 0, 3);
        this.add(pickUpButton, 0, 2);
    }

    public void setHealthPoints(int health) {
        healthPoints.setText(Integer.toString(health));
    }

    public Label getCharacterName() {
        return characterName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Button getPickUpButton() {
        return pickUpButton;
    }
}
