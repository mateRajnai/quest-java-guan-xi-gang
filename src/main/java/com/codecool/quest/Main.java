package com.codecool.quest;

import com.codecool.quest.display.Display;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.mapentities.Automaton;
import com.codecool.quest.logic.util.Vector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Display display = new Display(map);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = display.createScene();
        primaryStage.setScene(scene);
        display.refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(Vector.UP);
                break;
            case DOWN:
                map.getPlayer().move(Vector.DOWN);
                break;
            case LEFT:
                map.getPlayer().move(Vector.LEFT);
                break;
            case RIGHT:
                map.getPlayer().move(Vector.RIGHT);
                break;
            case E:
                map.getPlayer().interact();
        }
        map.getAutomatons().forEach(Automaton::operate);
        display.refresh();
    }
}
