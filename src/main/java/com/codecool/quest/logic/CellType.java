package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    HAMMER("hammer"),
    KEY("key"),
    DEAD_SKELETON("dead skeleton"),
    BONE("bone"),
    STAIRS_FROM_UP("stairs from up"),
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
