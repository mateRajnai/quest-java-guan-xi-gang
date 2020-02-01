package com.codecool.quest;

import com.codecool.quest.display.Display;
import com.codecool.quest.logic.BotControl;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.mapentities.Automaton;
import com.codecool.quest.logic.mapentities.actors.Player;
import com.codecool.quest.logic.mapentities.items.Item;
import com.codecool.quest.logic.util.Vector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Display display = new Display(map);
    BotControl botControl = new BotControl(this::actuateBots);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Codecool Quest");
        primaryStage.setOnCloseRequest(windowEvent -> botControl.deactivate());

        Scene scene = display.createScene();
        primaryStage.setScene(scene);

        display.refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.show();
        botControl.activate();
        display.focusLayout();
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
                interact();
        }
        display.refresh();
    }

    private void actuateBots() {
        map.getAutomatons().forEach(Automaton::operate);
        display.refresh();
    }

    private void interact() {
        Player player = map.getPlayer();
        if (player.getCell().hasItem()) {
            Item item = player.getCell().getItem();
            item.addToInventory();
            display.addToInventory(item);
        }
    }
}
