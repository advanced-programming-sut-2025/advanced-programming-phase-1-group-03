package com.ap.client.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum FishTypes {
    Salmon("Salmon", 75, Season.Fall, false),
    Sardine("Sardine", 40, Season.Fall, false),
    Shad("Shad", 60, Season.Fall, false),
    BlueDiscus("Blue Discus", 120, Season.Fall, false),
    MidnightCarp("Midnight Carp", 150, Season.Winter, false),
    Squid("Squid", 80, Season.Winter, false),
    Tuna("Tuna", 100, Season.Winter, false),
    Perch("Perch", 55, Season.Winter, false),
    Flounder("Flounder", 100, Season.Spring, false),
    Lionfish("Lionfish", 100, Season.Spring, false),
    Herring("Herring", 30, Season.Spring, false),
    Ghostfish("Ghostfish", 45, Season.Spring, false),
    Tilapia("Tilapia", 75, Season.Spring, false),
    Dorado("Dorado", 100, Season.Spring, false),
    Legend("Legend", 5000, Season.Spring, true),
    Glacierfish("Glacierfish", 1000, Season.Winter, true),
    Angler("Angler", 900, Season.Fall, true),
    Crimsonfish("Crimsonfish", 1500, Season.Summer, true);
    private String name;
    private int sellPrice;
    private Season season;
    private boolean fishingAbilityMustBeMax;
    private TextureRegion icon;
    FishTypes(String name, int price, Season season, boolean fishingAbilityMustBeMax) {
        this.name = name;
        this.sellPrice = price;
        this.season = season;
        this.fishingAbilityMustBeMax = fishingAbilityMustBeMax;
    }
    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return sellPrice;
    }

    public Season getSeason() {
        return season;
    }

    public static FishTypes getType(String name) {
        FishTypes fishTypes;
        try {
            fishTypes = FishTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return fishTypes;
    }

    public boolean isFishingAbilityMustBeMax() {
        return fishingAbilityMustBeMax;
    }
    public static FishTypes find(String name) {
        name = name.toLowerCase().replaceAll("\\s+", "");
        for (FishTypes value : values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }
}