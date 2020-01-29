package com.codecool.quest.layers;

import javafx.scene.control.*;

import java.util.Optional;

public class MessageLoader {
    private static TextInputDialog characterNameDialog;
    private static Alert endingAlert;

    static {
        initCharacterNameDialog();
        initEndingAlert();
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

    public static Optional<String> askForCharacterName() {
        return characterNameDialog.showAndWait();
    }

    public static void showEndingAlert() {
        endingAlert.showAndWait();
    }
}
