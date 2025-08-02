package com.ap.model;

public enum AbilityType {
    Farming(4),
    Mining(4),
    Foraging(4),
    Fishing(4),
    ;
    public final int maxLevel;

    AbilityType(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
