package com.codecool.quest.display;

public class ItemData {

    private String name;
    private Integer amount;

    public ItemData(String name) {
        this.name = name;
        this.amount = 1;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void decreaseAmount() {
        this.amount--;
    }

    public void increaseAmount() {
        this.amount++;
    }

    public void reset() {
        this.amount = 0;
    }
}
