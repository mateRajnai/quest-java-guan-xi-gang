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
    CHEST_OPEN("chest open"),
    CHEST_CLOSED("chest closed"),
    DOOR_CLOSED("door closed"),
    DOOR_OPENED("door opened");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isTraversable() {
        switch (this) {
            case FLOOR:
            case BONE:
            case GRASS2:
            case DEAD_SKELETON:
            case UPSTAIRS:
            case DOWNSTAIRS:
            case DOOR_OPENED:
                return true;
            default:
                return false;
        }
    }
}
