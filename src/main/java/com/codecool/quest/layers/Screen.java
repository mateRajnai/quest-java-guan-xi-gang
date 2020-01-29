package com.codecool.quest.layers;

import com.codecool.quest.Tiles;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.util.LengthUnit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Screen {

    private Layout layout;
    private GraphicsContext context;
    private GameMap map;
    private MapView mapView;

    public Screen(Layout layout, GameMap map) {
        this.layout = layout;
        this.context = layout.getCanvas().getGraphicsContext2D();
        this.map = map;
        this.mapView = new MapView(map);
    }

    public GameMap getMap() {
        return map;
    }

    public void updateMap(GameMap map) {
        this.map = map;
        this.mapView = new MapView(map);
    }

    public void focusLayout() {
        layout.requestFocus();
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, layout.getCanvasWidth(LengthUnit.PIXELS), layout.getCanvasHeight(LengthUnit.PIXELS));

        int minX = mapView.minX();
        int minY = mapView.minY();
        int maxY = minY + layout.getCanvasHeight(LengthUnit.CELLS);
        int maxX = minX + layout.getCanvasWidth(LengthUnit.CELLS);

        for (int col = 0, x = minX; x < maxX; x++, col++) {
            for (int row = 0, y = minY; y < maxY; y++, row++) {
                Cell cell = map.getCell(x, y);
                if (cell.hasActor()) {
                    Tiles.drawTile(context, cell.getActor(), col, row);
                } else if (cell.hasItem()) {
                    Tiles.drawTile(context, cell.getItem(), col, row);
                } else {
                    Tiles.drawTile(context, cell, col, row);
                }
            }
        }
        layout.getSidePanel().setHealthPoints(map.getPlayer().getHealth());
    }
}
