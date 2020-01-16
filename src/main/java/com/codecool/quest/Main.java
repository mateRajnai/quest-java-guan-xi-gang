package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Bat;
import com.codecool.quest.logic.actors.Duck;
import com.codecool.quest.logic.actors.Golem;
import com.codecool.quest.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label characterNameLabel = new Label("hackerman");
    ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        prepareStage(primaryStage);
        refresh();
        primaryStage.show();
        setCharacterName();
        activateBots();
    }

    private void prepareStage(Stage primaryStage) {
        Scene scene = createScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codecool Quest");
        primaryStage.setOnCloseRequest(windowEvent -> botActuator.shutdown());
    }

    private Scene createScene() {
        GridPane ui = createUI();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::onKeyPressed);
        return scene;
    }

    private GridPane createUI() {
        GridPane ui = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints(85);
        ui.getColumnConstraints().add(col1);

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Epic name: "), 0, 0);
        ui.add(characterNameLabel, 1, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);

        return ui;
    }

    private TextInputDialog createCharacterNameDialog() {
        TextInputDialog nameDialog = new TextInputDialog("hackerman");
        nameDialog.setTitle("Character setup");
        nameDialog.setHeaderText("Choose an epic name for your character!");
        nameDialog.setContentText("Epic name:");
        nameDialog.setGraphic(null);

        Button cancelButton = (Button) nameDialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setVisible(false);

        TextField nameInputField = nameDialog.getEditor();
        Button OKButton = (Button) nameDialog.getDialogPane().lookupButton(ButtonType.OK);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) -> {
            OKButton.setDisable(!(newValue.length() > 0 && newValue.length() <= 9));
        });

        return nameDialog;
    }

    private void setCharacterName() {
        TextInputDialog dialog = createCharacterNameDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> this.characterNameLabel.setText(name));
    }

    private void activateBots() {
        Runnable actuate = () -> Platform.runLater(() -> {
            Skeleton.getSkeletons().forEach(Skeleton::move);
            Bat.getBats().forEach(Bat::move);
            Duck.getDucks().forEach(Duck::move);
            Golem.getGolems().forEach(Golem::attackIfPlayerNextToIt);
            refresh();
        });
        botActuator.scheduleAtFixedRate(actuate, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                break;
        }
        refresh();
    }

    private void refresh() {
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
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
