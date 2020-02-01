package com.codecool.quest.display;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Display {

    GameMap map;
    Layout layout = new Layout();
    Canvas canvas;
    GraphicsContext context;
    Label healthLabel;

    public Display(GameMap map) {
        this.map = map;
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH
        );
        context = canvas.getGraphicsContext2D();
        layout.setCenter(canvas);
        healthLabel = layout.getHealthLabel();
    }

    public Scene createScene() {
        return new Scene(layout);
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.hasActor())
                    Tiles.drawTile(context, cell.getActor(), x, y);
                else if (cell.hasItem())
                    Tiles.drawTile(context, cell.getItem(), x, y);
                else
                    Tiles.drawTile(context, cell, x, y);
            }
        }
        healthLabel.setText(Integer.toString(map.getPlayer().getHealth()));
    }
}
