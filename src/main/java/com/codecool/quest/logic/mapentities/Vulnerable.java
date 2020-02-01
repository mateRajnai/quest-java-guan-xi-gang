package com.codecool.quest.logic.mapentities;

public interface Vulnerable {
    void terminate();

    int getHealth();

    void setHealth(int health);
}
