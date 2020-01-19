package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    DEAD_SKELETON("dead skeleton"),
    BONE("bone"),
    UPSTAIRS("stairs from up"),
    DOWNSTAIRS("stairs down"),
    CAMPFIRE("campfire"),
    BRONZE_TORCH("bronze torch"),
    DRIED_BRANCH("dried branch"),
    STONES("stones"),
    GRASS2("grass2"),
    POT("pot"),
    CHEST_OPEN("chest open"),
    CHEST_CLOSED("chest closed"),
    COINS("coins");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
