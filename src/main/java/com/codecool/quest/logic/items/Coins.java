package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Coins extends Item {

    private static List<Coins> coins = new ArrayList<>();

    public Coins(Cell cell) {
        super(cell);
        addCoins(this);
    }

    public static void addCoins(Coins coin) {
        coins.add(coin);
    }

    @Override
    public String getTileName() {
        return "coins";
    }
}
