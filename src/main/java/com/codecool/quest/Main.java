package com.codecool.quest;

import com.codecool.quest.logic.BotControl;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    UI ui = new UI(map);
    VisualFrameWork visuals = new VisualFrameWork(ui);
    BotControl botControl = new BotControl(visuals);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = visuals.getScene();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codecool quest");
        primaryStage.setOnCloseRequest(windowEvent -> botControl.deactivate());
        ui.initPickUpButton(visuals);
        ui.initInventory(visuals);
        visuals.refresh();
        primaryStage.show();
        visuals.focusLayout();
        ui.setCharacterName();
        botControl.activate();
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
            botControl.deactivate();
            if (MapLoader.hasNextLevel()) {
                map = MapLoader.loadMap();
            } else {
                ui.showEndingAlert();
                map = MapLoader.loadMap(1);
            }
            visuals.setMap(map);
            visuals.refresh();
            botControl.reactivate();
        }
    }
}
