package com.codecool.quest.layers;

import com.codecool.quest.logic.Inventory;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SidePanel extends VBox {

    private GridPane labelGrid = new GridPane();
    private Label healthLabel = new Label("Health: ");
    private Label characterNameLabel = new Label("Epic name: ");
    private Label healthPoints = new Label();
    private Label characterName = new Label("hackerman");
    private Inventory inventory = new Inventory();
    private Button pickUpButton = new Button("Pick up");
    private Label countdownTimer = new Label();
    private VBox vbox = new VBox();

    public SidePanel() {
        ColumnConstraints col1 = new ColumnConstraints(85);
        labelGrid.getColumnConstraints().add(col1);
        labelGrid.setPrefWidth(200);
        labelGrid.setPadding(new Insets(10));
        labelGrid.add(characterNameLabel, 0, 1);
        labelGrid.add(characterName, 1, 1);
        labelGrid.add(healthLabel, 0, 0);
        labelGrid.add(healthPoints, 1, 0);
        countdownTimer.setPrefWidth(200);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(inventory, pickUpButton, countdownTimer);
        this.getChildren().addAll(labelGrid, vbox);
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

    public Label getCountdownTimer() {
        return countdownTimer;
    }
}
