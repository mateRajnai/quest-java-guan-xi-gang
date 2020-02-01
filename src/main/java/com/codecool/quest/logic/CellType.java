package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty", true, true),
    FLOOR("floor", false, true),
    WALL("wall", true, false),
    UPSTAIRS("upstairs", false, false),
    DOWNSTAIRS("downstairs", false, true);

    private final String tileName;
    private final boolean isObstacle;
    private final boolean isTransparent;

    CellType(String tileName, boolean isObstacle, boolean isTransparent) {
        this.tileName = tileName;
        this.isObstacle = isObstacle;
        this.isTransparent = isTransparent;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
