package com.codecool.quest.layers;

import com.codecool.quest.Tiles;
import com.codecool.quest.util.LengthUnit;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class Layout extends BorderPane {

    private Canvas canvas;
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 640;

    private SidePanel sidePanel;

    public Layout() {
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        sidePanel = new SidePanel();
        this.setCenter(canvas);
        this.setRight(sidePanel);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getCanvasWidth(LengthUnit lengthUnit) {
        if (lengthUnit == LengthUnit.PIXELS)
            return CANVAS_WIDTH;
        else
            return CANVAS_WIDTH / Tiles.TILE_WIDTH;
    }

    public int getCanvasHeight(LengthUnit lengthUnit) {
        if (lengthUnit == LengthUnit.PIXELS)
            return CANVAS_HEIGHT;
        else
            return CANVAS_HEIGHT / Tiles.TILE_WIDTH;
    }

    public SidePanel getSidePanel() {
        return sidePanel;
    }
}
