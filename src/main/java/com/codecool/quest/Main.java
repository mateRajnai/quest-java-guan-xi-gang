package com.codecool.quest;

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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {
    GameMap map = MapLoader.loadMap(1);
    Visuals visuals;
    ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        visuals = new Visuals(map);
        Scene scene = visuals.getScene();
        initPickUpButton();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codecool quest");
        primaryStage.setOnCloseRequest(windowEvent -> botActuator.shutdown());
        visuals.refresh();
        primaryStage.show();
        visuals.getLayout().requestFocus();
        setCharacterName();
        activateBots();
    }

    private void initPickUpButton() {
        ObservableList<String> items = FXCollections.observableArrayList();
        visuals.getPickUpButton().setOnAction(actionEvent -> {
            addItemToInventory(map, "hammer", items);
            addItemToInventory(map, "key", items);
            addItemToInventory(map, "coin", items);
            pickUpCoins(items);
            visuals.getLayout().requestFocus();
        });
    }

    private void addItemToInventory(GameMap map, String itemToBeAdd, ObservableList<String> items) {
        try {
            if (map.getPlayer().getCell().getItem().pickUpItem(map, itemToBeAdd)) {
                items.add(itemToBeAdd);
                visuals.getInventory().setItems(items);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void pickUpCoins(ObservableList<String> items) {
        if (map.getPlayer().getCell().getTileName().equals("coins")) {
            items.add("coins");
            visuals.getInventory().setItems(items);
            map.getPlayer().getCell().setType(CellType.FLOOR);
        }
    }

    private void setCharacterName() {
        TextInputDialog dialog = visuals.getCharacterNameDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> visuals.setCharacterName(name));
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
        visuals.refresh();
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
        visuals.refresh();
        checkEndGame();
    }

    public void checkEndGame() {
        if (map.getPlayer().getCell().getType() == CellType.STAIRS_DOWN) {
            botActuator.shutdown();
            if (MapLoader.getCurrentLevel() == 1) {
                map = MapLoader.loadMap(2);
            } else {
                Alert gameWonAlert = visuals.getGameWonAlert();
                gameWonAlert.showAndWait();
                map = MapLoader.loadMap(1);
            }
            botActuator = Executors.newSingleThreadScheduledExecutor();
            visuals = new Visuals(map);
            visuals.refresh();
            activateBots();
        }
    }
}
