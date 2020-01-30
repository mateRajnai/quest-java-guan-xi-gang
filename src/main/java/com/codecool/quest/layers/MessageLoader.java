package com.codecool.quest.layers;

import javafx.scene.control.*;

import java.util.Optional;

public class MessageLoader {
    private static TextInputDialog characterNameDialog;
    private static Alert endingAlert;
    private static Alert gameOverAlert;

    static {
        initCharacterNameDialog();
        endingAlert = createAlert("Epic victory message", "You have reached the stairs of wisdom!");
        gameOverAlert = createAlert("Sad defeat message", "Game is over, my friend!");
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

    private static Alert createAlert(String title, String message) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION,
                message,
                ButtonType.OK
        );
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.setTitle(title);
        return alert;
    }

    public static Optional<String> askForCharacterName() {
        return characterNameDialog.showAndWait();
    }

    public static void showEndingAlert() {
        endingAlert.showAndWait();
    }

    public static void showGameOverAlert() {
        gameOverAlert.showAndWait();
    }
}
