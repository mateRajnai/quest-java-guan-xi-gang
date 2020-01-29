package com.codecool.quest.layers;

import com.codecool.quest.Tiles;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class VisualFrameWork {

    private Canvas canvas;
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 640;
    private GraphicsContext context;
    private BorderPane layout;

    private UI ui;
    private Scene scene;
    private MapView mapView;

    public VisualFrameWork(UI ui) {
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        context = canvas.getGraphicsContext2D();
        layout = new BorderPane();
        layout.setCenter(canvas);
        this.ui = ui;
        this.mapView = new MapView(ui.getMap());
        layout.setRight(ui);
        scene = new Scene(layout);
    }

    public void setMap(GameMap map) {
        ui.setMap(map);
        mapView = new MapView(map);
    }

    public Scene getScene() {
        return scene;
    }

    public void focusLayout() {
        layout.requestFocus();
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int minX = mapView.minX();
        int minY = mapView.minY();
        int maxY = minY + (CANVAS_HEIGHT / Tiles.TILE_WIDTH);
        int maxX = minX + (CANVAS_WIDTH / Tiles.TILE_WIDTH);

        for (int col = 0, x = minX; x < maxX; x++, col++) {
            for (int row = 0, y = minY; y < maxY; y++, row++) {
                Cell cell = ui.getMap().getCell(x, y);
                if (cell.hasActor()) {
                    Tiles.drawTile(context, cell.getActor(), col, row);
                } else if (cell.hasItem()) {
                    Tiles.drawTile(context, cell.getItem(), col, row);
                } else {
                    Tiles.drawTile(context, cell, col, row);
                }
            }
        }
        ui.setHealthPoints();
    }
}
