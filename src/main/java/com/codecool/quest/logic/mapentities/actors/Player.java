package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;
import com.codecool.quest.logic.util.Direction;

public class Player extends Actor implements Vulnerable {

    public Player(Cell cell) {
        super(cell);
    }

    public void move(Direction direction) {
        Cell nextCell = cell.getNeighbour(direction);
        if (canHit(nextCell))
            hit((Vulnerable) nextCell.getMapEntity());
        else if (canMoveTo(nextCell))
            moveTo(nextCell);
    }

    public void move(int dx, int dy) {
        this.move(new Direction(dx, dy));
    }

    @Override
    public boolean canHit(Cell cell) {
        return cell.hasMapEntity() && cell.getMapEntity() instanceof Vulnerable;
    }

    @Override
    public void terminate() {
        this.cell.setMapEntity(null);
        this.setCell(null);
    }

    public String getTileName() {
        return "player";
    }
}
