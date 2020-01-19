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
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {
    GameMap map = MapLoader.loadMap(1);
    VisualFrameWork visuals;
    UI ui;
    ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui = new UI(map);
        visuals = new VisualFrameWork(ui);
        Scene scene = visuals.getScene();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codecool quest");
        primaryStage.setOnCloseRequest(windowEvent -> botActuator.shutdown());
        visuals.refresh();
        primaryStage.show();
        VisualFrameWork.focusLayout();
        ui.setCharacterName();
        activateBots();
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
        if (map.getPlayer().getCell().getType() == CellType.DOWNSTAIRS) {
            botActuator.shutdown();
            if (MapLoader.getCurrentLevel() == 1) {
                map = MapLoader.loadMap(2);
            } else {
                ui.showEndingAlert();
                map = MapLoader.loadMap(1);
            }
            botActuator = Executors.newSingleThreadScheduledExecutor();
            ui.setMap(map);
            visuals.setUi(ui);
            visuals.refresh();
            activateBots();
        }
    }
}
