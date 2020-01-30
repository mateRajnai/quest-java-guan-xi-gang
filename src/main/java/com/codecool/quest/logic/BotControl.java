package com.codecool.quest.logic;

import com.codecool.quest.VisualFrameWork;
import com.codecool.quest.logic.actors.Bat;
import com.codecool.quest.logic.actors.Duck;
import com.codecool.quest.logic.actors.Golem;
import com.codecool.quest.logic.actors.Skeleton;
import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotControl {

    private static final int MONSTER_ACTION_PERIOD = 500;

    private VisualFrameWork visuals;
    private ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();
    private Runnable actuate = () -> Platform.runLater(this::actuateBots);

    public BotControl(VisualFrameWork visuals) {
        this.visuals = visuals;
    }

    private void actuateBots() {
        Skeleton.getSkeletons().forEach(Skeleton::move);
        Bat.getBats().forEach(Bat::move);
        Duck.getDucks().forEach(Duck::move);
        Golem.getGolems().forEach(Golem::attackIfPlayerNextToIt);
        visuals.refresh();
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
