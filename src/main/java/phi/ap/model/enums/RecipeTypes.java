package phi.ap.model.enums;

import phi.ap.model.ItemStack;
import phi.ap.model.items.products.Mineral;
import phi.ap.model.items.products.Stone;
import phi.ap.model.items.products.Wood;

import java.util.ArrayList;
import java.util.Arrays;

public enum RecipeTypes {
    // Cooking Recipes
    Hashbrowns("Hashbrowns"),
    Omelet("Omelet"),
    Pancakes("Pancakes"),
    Bread("Bread"),
    Tortilla("Tortilla"),
    Pizza("Pizza"),
    MakiRoll("MakiRoll"),
    TripleShotEspresso("TripleShotEspresso"),
    Cookie("Cookie"),
    FriedEgg("FriedEgg"),
    BakedFish("BakedFish"),
    Salad("Salad"),
    PumpkinPie("PumpkinPie"),
    Spaghetti("Spaghetti"),
    FruitSalad("FruitSalad"),
    RedPlate("RedPlate"),
    SalmonDinner("SalmonDinner"),
    VegetableMedley("VegetableMedley"),
    FarmersLunch("FFarmersLunch"),
    SurvivalBurger("SurvivalBurger"),
    DishOTheSea("DishOTheSea"),
    SeafoamPudding("SeafoamPudding"),
    MinersTreat("MinersTreat"),

    // Crafting Recipes

    CherryBomb("CherryBomb"),
    Bomb("Bomb"),
    MegaBomb("MegaBomb"),
    Sprinkler("Sprinkler"),
    QualitySprinkler("QualitySprinkler"),
    IridiumSprinkler("IridiumSprinkler"),
    Furnace("Furnace"),
    CharcoalKiln("CharcoalKiln"),
    Scarecrow("Scarecrow"),
    DeluxeScarecrow("DeluxeScarecrow"),
    BeeHouse("BeeHouse"),
    CheesePress("CheesePress"),
    Keg("Keg"),
    Loom("Loom"),
    MayonnaiseMachine("MayonnaiseMachine"),
    OilMaker("OilMaker"),
    PreservesJar("PreservesJar"),
    Dehydrator("Dehydrator"),
    GrassStarter("GrassStarter"),
    FishSmoker("FishSmoker")
    ;
    private final String name;
    RecipeTypes(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
