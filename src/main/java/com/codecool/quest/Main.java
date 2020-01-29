package com.codecool.quest;

import com.codecool.quest.layers.*;
import com.codecool.quest.logic.*;
import com.codecool.quest.logic.actors.TheBoss;
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
    CountdownTimer countdownTimer = new CountdownTimer(layout);

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
        countdownTimer.countdown();
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

        if (!MapLoader.hasNextLevel()
                && TheBoss.getTheBosses().size() == 0
                && countdownTimer.isTimeZero() && !TheBoss.getIsTheBossKilled()
        ) {
            TheBoss theBoss = new TheBoss(map.getCellOfFinish(CellType.DOWNSTAIRS.getTileName()));
            TheBoss.add(theBoss);
        }

        screen.refresh();
        checkEndGame();
    }

    public void checkEndGame() {
        if (map.getPlayer().getCell().getType() == CellType.DOWNSTAIRS) {
            botControl.deactivate();
            if (MapLoader.hasNextLevel()) {
                map = MapLoader.loadMap();
            } else if (TheBoss.getIsTheBossKilled() || !countdownTimer.isTimeZero()) {
                MessageLoader.showEndingAlert();
                map = MapLoader.loadMap(1);
                // After restarting the game these fields must be set up again
                TheBoss.setIsTheBossKilled(false);
                countdownTimer.setCountdownTimer();
                countdownTimer.countdown();
            }
            ui.updateMap(map);
            screen.refresh();
            botControl.reactivate();
        }
    }
}
