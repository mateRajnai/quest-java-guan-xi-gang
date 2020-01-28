package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Coins extends Item {

    private static List<Coins> coins = new ArrayList<>();

    public Coins(Cell cell) {
        super(cell);
        addCoins();
    }

    private void addCoins() {
        coins.add(this);
    }

    @Override
    public String getTileName() {
        return "coins";
    }
}
