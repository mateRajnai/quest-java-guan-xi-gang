package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.items.Coins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pot extends Actor {

    private static final int INITIAL_HEALTH = 1;
    private static List<Pot> pots = new ArrayList<>();

    Random random = new Random();

    public Pot(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
    }

    @Override
    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.FLOOR);
        rollForCoins();
        pots.removeIf(pot -> pot == this);
    }

    public void rollForCoins() {
        int rolledNumber = random.nextInt(100);
        if (rolledNumber > 0) {
            new Coins(this.getCell());
        }
    }

    public static void addPot(Pot pot) {
        pots.add(pot);
    }

    public static List<Pot> getPots() {
        return pots;
    }

    @Override
    public String getTileName() {
        return "pot";
    }
}
