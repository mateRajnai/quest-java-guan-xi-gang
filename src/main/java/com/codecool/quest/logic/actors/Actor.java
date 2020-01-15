package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public abstract void move(int dx, int dy);

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) { this.cell = cell;}

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
