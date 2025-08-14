package com.ap.model;

public enum Crafting {
    BeeHouse("BeeHouse"),
    Bomb("Bomb"),
    CharcoalKiln("CharcoalKiln"),
    CheesePress("CheesePress"),
    CherryBomb("CherryBomb"),
    Dehydrator("Dehydrator"),
    DeluxeScarecrow("DeluxeScarecrow"),
    FishSmoker("FishSmoker"),
    Furnace("Furnace"),
    GrassStarter("GrassStarter"),
    IridiumSprinkler("IridiumSprinkler"),
    Keg("Keg"),
    Loom("Loom"),
    MayonnaiseMachine("MayonnaiseMachine"),
    MegaBomb("MegaBomb"),
    MysticTreeSeed("MysticTreeSeed"),
    OilMaker("OilMaker"),
    PreservesJar("PreservesJar"),
    QualitySprinkler("QualitySprinkler"),
    Scarecrow("Scarecrow"),
    Sprinkler("Sprinkler");

    public final String name;

    Crafting(String name) {
        this.name = name;
    }

    public static Crafting getCraftingByName(String name) {
        for(Crafting crafting : Crafting.values()) {
            if(crafting.name.equals(name))
                return crafting;
        }
        return null;
    }
}

