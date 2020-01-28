package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Coins extends Item {

    private static List<Item> coins = new ArrayList<>();

    public Coins(Cell cell) {
        super(cell);
        addCoins();
    }

    @Override
    public List<Item> getInstances() {
        return coins;
    }

    private void addCoins() {
        coins.add(this);
    }

    @Override
    public String getTileName() {
        return "coins";
    }
}
