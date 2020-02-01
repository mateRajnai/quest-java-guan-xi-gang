package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.mapentities.items.Item;
import com.codecool.quest.logic.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor implements Vulnerable {

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 5;

    private List<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void acquire(Item item) {
        inventory.add(item);
    }

    public void move(Vector vector) {
        Cell nextCell = cell.getNeighbour(vector);
        if (canHit(nextCell))
            hit((Vulnerable) nextCell.getActor());
        else if (canMoveTo(nextCell))
            moveTo(nextCell);
    }

    public void move(int dx, int dy) {
        this.move(new Vector(dx, dy));
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
