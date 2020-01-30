package com.codecool.quest.util;

import javafx.event.EventType;

public class GameOverEvent extends GameEvent {

    public static final EventType<GameOverEvent> GAME_OVER_EVENT = new EventType<>(GAME_EVENT, "GameOverEvent");

    public GameOverEvent() {
        super(GAME_OVER_EVENT);
    }

    public void invokeHandler(GameEventHandler handler) {
        handler.onGameOver();
    }
}
