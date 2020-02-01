package com.codecool.quest.logic;

import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotControl {

    private static final long TIME_STEP = 400;
    private ScheduledExecutorService service;
    private Runnable actuateBots;

    public BotControl(Runnable actuateBots) {
        this.actuateBots = () -> Platform.runLater(actuateBots);
    }

    public void activate() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(actuateBots, 0, TIME_STEP, TimeUnit.MILLISECONDS);
    }

    public void deactivate() {
        service.shutdown();
    }

    public void reactivate() {
        deactivate();
        activate();
    }
}
