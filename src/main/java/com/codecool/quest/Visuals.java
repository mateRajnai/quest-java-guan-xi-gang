package com.codecool.quest;

import javafx.scene.control.*;

public class Visuals {
    private TextInputDialog characterNameDialog;
    private Alert gameWonAlert;

    public Visuals() {
        setCharacterNameDialog();
        setGameWonAlert();
    }

    private void setCharacterNameDialog() {
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

    public TextInputDialog getCharacterNameDialog() {
        return characterNameDialog;
    }

    public Alert getGameWonAlert() {
        return gameWonAlert;
    }
}
