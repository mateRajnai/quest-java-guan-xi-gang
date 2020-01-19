package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class VisualFrameWork {

    private static Canvas canvas;
    private static GraphicsContext context;

    private GameMap map;
    private UI ui;
    private static BorderPane layout = new BorderPane();
    private Scene scene;

    static {
        int canvasWidth = 800;
        int canvasHeight = 640;
        canvas = new Canvas(canvasWidth, canvasHeight);
        context = canvas.getGraphicsContext2D();
    }

    public VisualFrameWork(GameMap map) {
        this.map = map;
        layout.setCenter(canvas);
        ui = new UI(map);
        layout.setRight(ui);
        scene = new Scene(layout);
    }

    public Scene getScene() {
        return scene;
    }

    public UI getUi() {
        return ui;
    }

    public static void focusLayout() {
        layout.requestFocus();
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        ui.setHealthPoints();
    }
}
