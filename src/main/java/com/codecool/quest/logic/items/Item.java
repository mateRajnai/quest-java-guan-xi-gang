package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.actors.Player;

public abstract class Item implements Drawable {
    private Cell cell;
    private Player player;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public void setCell(Cell cell) { this.cell = cell; }

    public Cell getCell() { return cell; }

    public boolean pickUpItem(GameMap map, String itemToBePickedUp) {
        int playerPositionX = map.getPlayer().getX();
        int playerPositionY = map.getPlayer().getY();
        Cell cell = map.getCell(playerPositionX, playerPositionY);
        System.out.println(cell.getTileName());
        if (cell.getTileName().equals(itemToBePickedUp)) {
            cell.setItem(null);
            cell.setType(CellType.FLOOR);
            return true;
        }
        return false;
    }

}
