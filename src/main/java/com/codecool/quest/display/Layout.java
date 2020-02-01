package com.codecool.quest.display;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Layout extends BorderPane {

    private Label healthLabel = new Label();
    private GridPane sidePanel = new GridPane();
    private Canvas canvas;

    public Layout(int canvasWidth, int canvasHeight) {
        canvas = new Canvas(canvasWidth, canvasHeight);

        sidePanel.setPrefWidth(200);
        sidePanel.setPadding(new Insets(10));

        sidePanel.add(new Label("Health: "), 0, 0);
        sidePanel.add(healthLabel, 1, 0);

        this.setRight(sidePanel);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public GridPane getSidePanel() {
        return sidePanel;
    }
}
