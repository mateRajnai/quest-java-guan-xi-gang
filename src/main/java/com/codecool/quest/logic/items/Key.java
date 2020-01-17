package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Key extends Item {

    private static List<Key> keys = new ArrayList<>();

    public Key(Cell cell) {
        super(cell);
    }

    public static void addKey(Key key) {
        keys.add(key);
    }

    public void openDoor(Key key) {

    }

    @Override
    public String getTileName() {
        return "key";
    }
}
