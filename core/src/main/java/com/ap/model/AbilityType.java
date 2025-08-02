package com.ap.model;

public enum AbilityType {
    Farming(10),
    Mining(10),
    Foraging(10),
    Fishing(10),
    ;
    public final int maxLevel;

    AbilityType(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
