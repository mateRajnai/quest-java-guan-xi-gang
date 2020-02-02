package com.codecool.quest.logic.util;

public class Compass {

    private Cycle<Direction> cycle = new Cycle<>();

    public Compass() {
        for (CardinalDirection cardinalDirection : CardinalDirection.values())
            cycle.add(cardinalDirection.get());
    }

    public Compass(CardinalDirection cardinalDirection) {
        this();
        while (!cycle.getNext().equals(cardinalDirection.get()))
            cycle.forward();
    }

    public Direction getCurrent() {
        return cycle.current();
    }

    public Direction getNext() {
        return cycle.getNext();
    }

    public void forward() {
        cycle.forward();
    }
}
