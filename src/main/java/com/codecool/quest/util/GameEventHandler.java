package com.codecool.quest.util;

import javafx.event.EventHandler;

public abstract class GameEventHandler implements EventHandler<GameEvent> {

    public abstract void onGameOver();

    @Override
    public void handle(GameEvent gameEvent) {
        gameEvent.invokeHandler(this);
    }
}
