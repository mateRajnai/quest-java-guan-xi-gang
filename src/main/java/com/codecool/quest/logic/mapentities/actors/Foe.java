package com.codecool.quest.logic.mapentities.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.mapentities.Vulnerable;

public abstract class Foe extends Actor {
    public Foe(Cell cell) {
        super(cell);
    }

    @Override
    public boolean canHit(Cell cell) {
        return cell.getMapEntity() instanceof Player;
    }

    @Override
    public void hit(Vulnerable target) {
        super.hit(target);
    }
}
