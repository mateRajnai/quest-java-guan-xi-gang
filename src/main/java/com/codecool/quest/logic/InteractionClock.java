package com.codecool.quest.logic;

public class InteractionClock {
    private long timeSinceLastKeyPress = 0;
    private long now;
    private static final long THRESHOLD_MILLIS = 50;

    public void updateLastKeyPressTime() {
        timeSinceLastKeyPress = now;
    }

    public boolean isInteractionDenied() {
        now = System.currentTimeMillis();
        return (now - timeSinceLastKeyPress < THRESHOLD_MILLIS);
    }
}
