package com.codecool.quest;

import com.codecool.quest.layers.Layout;
import com.codecool.quest.layers.MessageLoader;
import com.codecool.quest.layers.Screen;
import com.codecool.quest.layers.UI;
import com.codecool.quest.logic.BotControl;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Combative;
import com.codecool.quest.logic.actors.TheBoss;
import com.codecool.quest.util.Direction;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Game {

    private Layout layout = new Layout();
    private GameMap map = MapLoader.loadMap();
    private Screen screen = new Screen(layout, map);
    private UI ui = new UI(screen);
    private TheBossClock theBossClock = new TheBossClock(screen);
    private InteractionClock interactionClock = new InteractionClock();
    private Stage stage;
    private BotControl botControl;

    public Game(Stage stage) {
        Scene scene = new Scene(layout);
        this.stage = stage;
        this.botControl = new BotControl(screen);
        this.botControl.setActuation(this::actuateBots);
        this.stage.setTitle("Codecool quest");
        this.stage.setScene(scene);
        this.stage.setOnCloseRequest(windowEvent -> botControl.deactivate());
        scene.setOnKeyPressed(this::onKeyPressed);
        screen.setOnPlayerDeath(this::onPlayerDeath);
        Runnable bossHatchery = () -> map.add(new TheBoss(map.getExitCell()));
        theBossClock.setBossHatchery(bossHatchery);
    }

    public void init() {
        screen.refresh();
        stage.show();
        screen.focusLayout();
        ui.setCharacterName();
        botControl.activate();
        theBossClock.countdown();
    }

    private void actuateBots() {
        map.getCombativeActors().forEach(Combative::act);
        screen.refresh();
    }

    private void onPlayerDeath() {
        botControl.deactivate();
        MessageLoader.showGameOverAlert();
        reset();
        resume();
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        if (interactionClock.isInteractionDenied()) return;

        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(Direction.UP);
                break;
            case DOWN:
                map.getPlayer().move(Direction.DOWN);
                break;
            case LEFT:
                map.getPlayer().move(Direction.LEFT);
                break;
            case RIGHT:
                map.getPlayer().move(Direction.RIGHT);
                break;
            case E:
                ui.interact();
                break;
        }

        interactionClock.updateLastKeyPressTime();
        screen.refresh();
        checkEndGame();
    }

    private void checkEndGame() {
        if (map.getPlayer().getCell().getType() == CellType.DOWNSTAIRS) {
            botControl.deactivate();
            if (MapLoader.hasNextLevel()) {
                map = MapLoader.loadMap();
            } else if (TheBoss.isDefeated() || theBossClock.isCounting()) {
                MessageLoader.showEndingAlert();
                reset();
            }
            resume();
        }
    }

    private void resume() {
        ui.updateMap(map);
        screen.updateMap(map);
        screen.refresh();
        botControl.reactivate();
    }

    private void reset() {
        ui.clearInventory();
        map = MapLoader.loadMap(1);
        theBossClock.reset();
    }
}
