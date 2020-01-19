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
    private static BorderPane layout = new BorderPane();

    private UI ui;
    private Scene scene;

    static {
        int canvasWidth = 800;
        int canvasHeight = 640;
        canvas = new Canvas(canvasWidth, canvasHeight);
        context = canvas.getGraphicsContext2D();
        layout.setCenter(canvas);
    }

    public VisualFrameWork(UI ui) {
        this.ui = ui;
        layout.setRight(ui);
        scene = new Scene(layout);
    }

    public Scene getScene() {
        return scene;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public static void focusLayout() {
        layout.requestFocus();
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < ui.getMap().getWidth(); x++) {
            for (int y = 0; y < ui.getMap().getHeight(); y++) {
                Cell cell = ui.getMap().getCell(x, y);
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
