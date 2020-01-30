package com.codecool.quest.logic;

import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotControl {

    private static final int MONSTER_ACTION_PERIOD = 400;

    private ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();
    private Runnable actuation;

    public void setActuation(Runnable actuation) {
        this.actuation = () -> Platform.runLater(actuation);
    }

    public void activate() {
        botActuator.scheduleAtFixedRate(actuation, 0, MONSTER_ACTION_PERIOD, TimeUnit.MILLISECONDS);
    }

    public void deactivate() {
        botActuator.shutdown();
    }

    public void reactivate() {
        botActuator = Executors.newSingleThreadScheduledExecutor();
        activate();
    }
}
