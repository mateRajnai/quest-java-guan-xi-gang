package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Key extends Item {

    private static List<Item> keys = new ArrayList<>();

    public Key(Cell cell) {
        super(cell);
        addKey(this);
    }

    @Override
    public List<Item> getInstances() {
        return keys;
    }

    public static void addKey(Key key) {
        keys.add(key);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
