package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty", true),
    FLOOR("floor", false),
    WALL("wall", true);

    private final String tileName;
    private final boolean isObstacle;

    CellType(String tileName, boolean isObstacle) {
        this.tileName = tileName;
        this.isObstacle = isObstacle;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isObstacle() {
        return isObstacle;
    }
}
