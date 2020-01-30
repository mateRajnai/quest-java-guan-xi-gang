package com.codecool.quest.logic;

import com.codecool.quest.layers.Screen;
import com.codecool.quest.logic.actors.*;
import com.codecool.quest.util.GameEventHandler;
import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotControl {

    private static final int MONSTER_ACTION_PERIOD = 400;

    private Screen screen;
    private ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();
    private Runnable actuate = () -> Platform.runLater(this::actuateBots);
    private GameEventHandler gameEventHandler;

    public BotControl(Screen screen) {
        this.screen = screen;
    }

    public void setGameEventHandler(GameEventHandler gameEventHandler) {
        this.gameEventHandler = gameEventHandler;
    }

    private void actuateBots() {
        Skeleton.getSkeletons().forEach(Skeleton::move);
        Bat.getBats().forEach(Bat::move);
        Duck.getDucks().forEach(Duck::move);
        Golem.getGolems().forEach(Golem::attackIfPlayerNextToIt);
        if (!TheBoss.isDormant())
            TheBoss.getTheBoss().move();
        screen.refresh();
        if (screen.getMap().getPlayer().isDead())
            screen.getMap().getPlayer().getGameOverEvent().invokeHandler(gameEventHandler);
    }

    public void activate() {
        botActuator.scheduleAtFixedRate(actuate, 0, MONSTER_ACTION_PERIOD, TimeUnit.MILLISECONDS);
    }

    public void deactivate() {
        botActuator.shutdown();
    }

    public void reactivate() {
        botActuator = Executors.newSingleThreadScheduledExecutor();
        activate();
    }
}
