package com.codecool.quest.display;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Layout extends BorderPane {

    private Label healthLabel = new Label();
    private VBox sidePanel = new VBox();
    private InventoryView inventoryView = new InventoryView();
    private Canvas canvas;

    public Layout(int canvasWidth, int canvasHeight) {
        canvas = new Canvas(canvasWidth, canvasHeight);

        GridPane labelGrid = new GridPane();
        labelGrid.setPrefWidth(200);
        labelGrid.setPadding(new Insets(10));

        labelGrid.add(new Label("Health: "), 0, 0);
        labelGrid.add(healthLabel, 1, 0);

        sidePanel.getChildren().addAll(labelGrid, inventoryView);
        this.setRight(sidePanel);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public VBox getSidePanel() {
        return sidePanel;
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }
}
