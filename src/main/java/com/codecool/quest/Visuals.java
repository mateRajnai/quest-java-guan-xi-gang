package com.codecool.quest;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Visuals {
    private Alert gameWonAlert;

    public Visuals() {
        setGameWonAlert();
    }

    private void setGameWonAlert() {
        gameWonAlert = new Alert(
                Alert.AlertType.INFORMATION,
                "You have reached the stairs of wisdom!",
                ButtonType.OK
        );
        gameWonAlert.setGraphic(null);
        gameWonAlert.setHeaderText(null);
        gameWonAlert.setTitle("Epic victory message");
    }

    public Alert getGameWonAlert() {
        return gameWonAlert;
    }
}
