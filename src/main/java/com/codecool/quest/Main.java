package com.codecool.quest;

import com.codecool.quest.layers.*;
import com.codecool.quest.logic.BotControl;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {
    Layout layout = new Layout();
    GameMap map = MapLoader.loadMap();
    Screen screen = new Screen(layout, map);
    UI ui = new UI(screen);
    BotControl botControl = new BotControl(screen);
    Scene scene = new Scene(layout);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Codecool quest");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(windowEvent -> botControl.deactivate());
        scene.setOnKeyPressed(this::onKeyPressed);
        screen.refresh();
        primaryStage.show();
        screen.focusLayout();
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
            case E:
                ui.interact();
                break;
        }
        screen.refresh();
        checkEndGame();
    }

    public void checkEndGame() {
        if (map.getPlayer().getCell().getType() == CellType.DOWNSTAIRS) {
            botControl.deactivate();
            if (MapLoader.hasNextLevel()) {
                map = MapLoader.loadMap();
            } else {
                MessageLoader.showEndingAlert();
                map = MapLoader.loadMap(1);
            }
            screen.updateMap(map);
            screen.refresh();
            botControl.reactivate();
        }
    }
}
