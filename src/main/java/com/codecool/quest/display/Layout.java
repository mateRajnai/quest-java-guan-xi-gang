package com.codecool.quest.display;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Layout extends BorderPane {

    Label healthLabel = new Label();
    GridPane sidePanel = new GridPane();

    public Layout() {
        sidePanel.setPrefWidth(200);
        sidePanel.setPadding(new Insets(10));

        sidePanel.add(new Label("Health: "), 0, 0);
        sidePanel.add(healthLabel, 1, 0);

        this.setRight(sidePanel);
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public GridPane getSidePanel() {
        return sidePanel;
    }
}
