package com.codecool.quest.util;

public enum OffsetMatrix {
    LEAVE(0, 0, 1, 1),
    CENTER(0.25f, 0.25f, 0.5f, 0.5f),
    CONTRACT(0.1f, 0.1f, 0.8f, 0.8f),
    HINT_CROSSHAIR(-0.4f, -0.4f, 0.8f, 0.8f),
    HINT_CONTENT(-0.3f, -0.3f, 0.6f, 0.6f);

    public final float kdx, kdy, kw, kh;

    OffsetMatrix(float kdx, float kdy, float kw, float kh) {
        this.kdx = kdx;
        this.kdy = kdy;
        this.kw = kw;
        this.kh = kh;
    }
}
