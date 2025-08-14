package com.ap.model;

public enum Crafting {
    Bee_House("BeeHouse"),
    Bomb("Bomb"),
    Charcoal_Kiln("CharcoalKiln"),
    Cheese_Press("CheesePress"),
    Cherry_Bomb("CherryBomb"),
    Dehydrator("Dehydrator"),
    Deluxe_Scarecrow("DeluxeScarecrow"),
    Fish_Smoker("FishSmoker"),
    Furnace("Furnace"),
    Grass_Starter("GrassStarter"),
    Iridium_Sprinkler("IridiumSprinkler"),
    Keg("Keg"),
    Loom("Loom"),
    Mayonnaise_Machine("MayonnaiseMachine"),
    Mega_Bomb("MegaBomb"),
    Mystic_Tree_Seed("MysticTreeSeed"),
    Oil_Maker("OilMaker"),
    Preserves_Jar("PreservesJar"),
    Quality_Sprinkler("QualitySprinkler"),
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

