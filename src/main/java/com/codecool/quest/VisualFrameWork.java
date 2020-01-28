package com.codecool.quest;

import com.codecool.quest.logic.Cell;
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

    public VisualFrameWork(UI ui) {
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        context = canvas.getGraphicsContext2D();
        layout = new BorderPane();
        layout.setCenter(canvas);
        this.ui = ui;
        layout.setRight(ui);
        scene = new Scene(layout);
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
        for (int x = 0; x < ui.getMap().getWidth(); x++) {
            for (int y = 0; y < ui.getMap().getHeight(); y++) {
                Cell cell = ui.getMap().getCell(x, y);
                if (cell.hasActor()) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.hasItem()) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        ui.setHealthPoints();
    }
}
