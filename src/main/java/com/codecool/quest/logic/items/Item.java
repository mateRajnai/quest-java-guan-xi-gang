package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.actors.Player;

public abstract class Item implements Drawable {
    private Cell cell;
    private Player player;
    private boolean isInInventory;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
        this.isInInventory = false;
    }

    public void setCell(Cell cell) { this.cell = cell; }

    public boolean isInInventory() {
        return isInInventory;
    }

    public Cell getCell() { return cell; }

    public boolean pickUpItem(GameMap map, String itemToBePickedUp) {
        int playerPositionX = map.getPlayer().getX();
        int playerPositionY = map.getPlayer().getY();
        Cell cell = map.getCell(playerPositionX, playerPositionY);
        if (cell.getTileName().equals(itemToBePickedUp)) {
            cell.setItem(null);
            cell.setType(CellType.FLOOR);
            return true;
        }
        return false;
    }

}
