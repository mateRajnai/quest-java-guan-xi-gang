package com.codecool.quest;

import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.Inventory;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Optional;

public class UI extends GridPane {

    private static Label healthLabel = new Label("Health: ");
    private static Label characterNameLabel = new Label("Epic name: ");
    private static Label countdownTimer = new Label();

    private static TextInputDialog characterNameDialog;
    private static Alert endingAlert;

    private GameMap map;
    private Label healthPoints = new Label();
    private Label characterName = new Label("hackerman");
    private Inventory inventory = new Inventory();
    private Button pickUpButton = new Button("Pick up");

    private final Integer startTime = 20;
    private Integer secondsLeft = startTime;


    static {
        initCharacterNameDialog();
        initGameWonAlert();
    }

    public UI(GameMap map) {
        this.map = map;

        ColumnConstraints col1 = new ColumnConstraints(115);
        this.getColumnConstraints().add(col1);
        this.setPrefWidth(200);
        this.setPadding(new Insets(10));
        this.add(characterNameLabel, 0, 1);
        this.add(characterName, 1, 1);
        this.add(healthLabel, 0, 0);
        this.add(healthPoints, 1, 0);
        this.add(inventory, 0, 3);
        this.add(pickUpButton, 0, 2);
        this.add(countdownTimer, 0, 4);
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

    private static void initGameWonAlert() {
        endingAlert = new Alert(
                Alert.AlertType.INFORMATION,
                "You have reached the stairs of wisdom!",
                ButtonType.OK
        );
        endingAlert.setGraphic(null);
        endingAlert.setHeaderText(null);
        endingAlert.setTitle("Epic victory message");
    }

    public void initInventory(VisualFrameWork visuals) {
        inventory.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                Item selectedItem = inventory.getSelectionModel().getSelectedItem();
                if (selectedItem instanceof Key && map.getPlayer().isDoorInNeighbourCell()) {
                    map.getPlayer().openDoorInNeighbourCell();
                    int indexOfKey = inventory.getSelectionModel().getSelectedIndex();
                    inventory.getItems().remove(indexOfKey);
                    visuals.focusLayout();
                }
            }
        });
    }

    public void initPickUpButton(VisualFrameWork visuals) {
        pickUpButton.setOnAction(actionEvent -> {
            handlePickUpButtonClick();
            visuals.focusLayout();
        });
    }

    private void handlePickUpButtonClick() {
        Item item = map.getPlayer().getCell().getItem();
        if (item != null) inventory.add(item);
    }

    public void setHealthPoints() {
        healthPoints.setText(Integer.toString(map.getPlayer().getHealth()));
    }

    public void setCharacterName() {
        Optional<String> result = characterNameDialog.showAndWait();
        result.ifPresent(name -> characterName.setText(name));
    }

    public void showEndingAlert() {
        endingAlert.showAndWait();
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public boolean isTimeZero() {
        return secondsLeft == 0;
    }

    public void countdown() {
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);


        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                secondsLeft--;
                countdownTimer.setText("Wait " + secondsLeft.toString() + " seconds\nif you want to\nfight the Boss");
                if (secondsLeft <= 0) {
                    time.stop();
                }
            }
        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public void setCountdownTimer() {
        this.secondsLeft = startTime;
    }
}
