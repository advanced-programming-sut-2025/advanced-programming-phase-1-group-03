package com.ap.client.model;

public enum AbilityType {
    Farming("Levels are gained by harvesting crops and caring for animals.\nEach level grants +1 hoe and watering can proficiency."),
    Mining("Mining skill is increased by breaking rocks\n(normally done with a Pickaxe).\nEach level grants +1 pickaxe proficiency."),
    Foraging("Foraging skill includes both gathered foraged goods,\nand wood from trees chopped with an axe tool.\nEach level grants +1 axe proficiency."),
    Fishing("Fishing is associated with successfully\ncompleting the fishing mini-game or catching fish in a Crab Pot,\nincreasing the fishing skill.\nEach level grants +1 fishing rod proficiency."),
    ;
    public static final int maxLevel = 4;

    private final String description;

    AbilityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
