package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.Inventory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Visuals {

    private static Canvas canvas;
    private static GraphicsContext context;
    private static GridPane ui = new GridPane();
    private static BorderPane layout = new BorderPane();
    private static Scene scene;

    private static Label healthLabel = new Label("Health: ");
    private static Label healthPoints = new Label();
    private static Label characterNameLabel = new Label("Epic name: ");
    private static Label characterName = new Label("hackerman");
//    private static Button pickUpButton = new Button("Pick up");

    private static TextInputDialog characterNameDialog;
    private static Alert gameWonAlert;

    private GameMap map;
    private static Inventory inventory = new Inventory();

    static {
        int canvasWidth = 800;
        int canvasHeight = 640;
        canvas = new Canvas(canvasWidth, canvasHeight);
        context = canvas.getGraphicsContext2D();
        initUI();
        layout.setCenter(canvas);
        layout.setRight(ui);
        scene = new Scene(layout);
        initCharacterNameDialog();
        initGameWonAlert();
    }

    public Visuals(GameMap map) {
        this.map = map;
    }

    private static void initUI() {
        ColumnConstraints col1 = new ColumnConstraints(85);
        ui.getColumnConstraints().add(col1);

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(characterNameLabel, 0, 1);
        ui.add(characterName, 1, 1);
        ui.add(healthLabel, 0, 0);
        ui.add(healthPoints, 1, 0);

//        ui.add(pickUpButton, 0, 2);
        ui.add(inventory.asListView(), 0, 3);
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
        gameWonAlert = new Alert(
                Alert.AlertType.INFORMATION,
                "You have reached the stairs of wisdom!",
                ButtonType.OK
        );
        gameWonAlert.setGraphic(null);
        gameWonAlert.setHeaderText(null);
        gameWonAlert.setTitle("Epic victory message");
    }

    public BorderPane getLayout() {
        return layout;
    }

    public Scene getScene() {
        return scene;
    }

    public void setCharacterName(String name) {
        characterName.setText(name);
    }

    public TextInputDialog getCharacterNameDialog() {
        return characterNameDialog;
    }

    public Alert getGameWonAlert() {
        return gameWonAlert;
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthPoints.setText(Integer.toString(map.getPlayer().getHealth()));
    }
}
