package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.mapentities.items.Item;
import com.codecool.quest.logic.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor implements Vulnerable {

    private List<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void acquire(Item item) {
        inventory.add(item);
    }

    public void move(Direction direction) {
        Cell nextCell = cell.getNeighbour(direction);
        if (canHit(nextCell))
            hit((Vulnerable) nextCell.getActor());
        else if (canMoveTo(nextCell))
            moveTo(nextCell);
    }

    public void move(int dx, int dy) {
        this.move(new Direction(dx, dy));
    }

    @Override
    public boolean canHit(Cell cell) {
        return cell.getActor() instanceof Vulnerable;
    }

    @Override
    public void terminate() {
        this.cell.setActor(null);
        this.setCell(null);
    }

    public String getTileName() {
        return "player";
    }
}
