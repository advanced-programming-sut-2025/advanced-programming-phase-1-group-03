package phi.ap.model.enums;

import phi.ap.model.ItemStack;
import phi.ap.model.items.products.Mineral;
import phi.ap.model.items.products.Stone;
import phi.ap.model.items.products.Wood;

import java.util.ArrayList;
import java.util.Arrays;

public enum RecipeTypes {
    // Artisan food
    Mayonnaise("Mayonnaise"),
    DuckMayonnaise("DuckMayonnaise"),
    DinosaurMayonnaise(" DinosaurMayonnaise"),
    Oil("Oil"),
    TruffleOil("TruffleOil"),
    Pickles("Pickles"),
    Jelly("Jelly"),
    SmokedFish("SmokedFish"),
    Beer("Beer"),
    Vinegar("Vinegar"),
    Coffee("Coffee"),
    Juice("Juice"),
    Mead("Mead"),
    PaleAle("PaleAle"),
    Wine("Wine"),
    Honey("Honey"),
    Cheese("Cheese"),
    CheeseWithLargeMilk("CheeseWithLargeMilk"),
    GoatCheese("GoatCheese"),
    GoatCheeseWithLargeMilk("GoatCheeseWithLargeMilk"),
    DriedMushrooms("DriedMushrooms"),
    DriedFruit("DriedFruit"),
    Raisins("Raisins"),
    Hay("Hay"),
    JojaCola("JojaCola"),
    Sugar("Sugar"),
    WheatFlour("WheatFlour"),
    Rice("Rice"),
    // Cooking Recipes
    HashBrowns("Hashbrowns"),
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
    FarmersLunch("FarmersLunch"),
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
