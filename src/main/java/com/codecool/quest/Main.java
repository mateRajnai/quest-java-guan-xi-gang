package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Bat;
import com.codecool.quest.logic.actors.Duck;
import com.codecool.quest.logic.actors.Golem;
import com.codecool.quest.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    Button pickUpButton = new Button("Pick up");
    Visuals visuals = new Visuals();

    ListView<String> inventory = new ListView<>();

    Label characterNameLabel = new Label("hackerman");
    ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = createUI();
        BorderPane borderPane = createBorderPane(ui);
        Scene scene = createScene(borderPane);
        prepareStage(primaryStage, scene);
        refresh();
        primaryStage.show();
        borderPane.requestFocus();
        setCharacterName();
        activateBots();
    }

    private void prepareStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codecool Quest");
        primaryStage.setOnCloseRequest(windowEvent -> botActuator.shutdown());
    }

    private Scene createScene(BorderPane borderPane) {
        ObservableList<String> items = FXCollections.observableArrayList();
        pickUpButton.setOnAction(actionEvent -> {
            addItemToInventory(map, "hammer", items);
            addItemToInventory(map, "key", items);
            addItemToInventory(map, "coin", items);
            borderPane.requestFocus();
        });

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::onKeyPressed);
        return scene;
    }

    private BorderPane createBorderPane(GridPane ui) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        return borderPane;
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

        ui.add(pickUpButton, 0, 2);
        ui.add(inventory, 0, 3);
        inventory.setPrefWidth(30);
        inventory.setPrefHeight(70);

        return ui;
    }

    private void addItemToInventory(GameMap map, String itemToBeAdd, ObservableList<String> items) {
        try {
            if (map.getPlayer().getCell().getItem().pickUpItem(map, itemToBeAdd)) {
                items.add(itemToBeAdd);
                inventory.setItems(items);
            }
        } catch (NullPointerException ignored) {
        }
        if (map.getPlayer().getCell().getTileName().equals(itemToBeAdd))
            items.add(itemToBeAdd);
    }

    private void setCharacterName() {
        TextInputDialog dialog = visuals.getCharacterNameDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> this.characterNameLabel.setText(name));
    }

    private void activateBots() {
        Runnable actuate = () -> Platform.runLater(this::actuateBots);
        botActuator.scheduleAtFixedRate(actuate, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void actuateBots() {
        Skeleton.getSkeletons().forEach(Skeleton::move);
        Bat.getBats().forEach(Bat::move);
        Duck.getDucks().forEach(Duck::move);
        Golem.getGolems().forEach(Golem::attackIfPlayerNextToIt);
        refresh();
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
        checkEndGame();
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

    public void checkEndGame() {
        if (map.getPlayer().getCell().getType() == CellType.STAIRS_DOWN) {
            botActuator.shutdown();
            botActuator = Executors.newSingleThreadScheduledExecutor();
            Alert gameWonAlert = visuals.getGameWonAlert();
            gameWonAlert.showAndWait();
            map = MapLoader.loadMap();
            refresh();
            activateBots();
        }
    }
}
