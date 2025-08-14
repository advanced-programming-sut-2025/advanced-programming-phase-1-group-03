package com.ap.model;

public enum Cookings {
    BakedFish("Baked Fish", "Baked_Fish", "Baked fish on a bed of herbs.", FoodRecipes.BakedFish),
    Bread("Bread", "Bread", "Bread! Breadmaking can be a very complex form of art, but I'll make it simple for you.", FoodRecipes.Bread),
    Cookie("Cookie", "Cookie", "Cookie! Sweet treat that boosts morale.", FoodRecipes.Cookie),
    DishOTheSea("Dish O' The Sea", "Dish_O%27_The_Sea", "Dish O' The Sea boosts fishing skill.", FoodRecipes.DishOTheSea),
    FarmersLunch("Farmer's Lunch", "Farmer%27s_Lunch", "Farmer's Lunch boosts farming.", FoodRecipes.FarmersLunch),
    FriedEgg("Fried Egg", "Fried_Egg", "Fried Egg is known by the player upon starting a new save file.", FoodRecipes.FriedEgg),
    FruitSalad("Fruit Salad", "Fruit_Salad", "Fruit Salad! Here's a healthy and delicious treat to brighten up your day.", FoodRecipes.FruitSalad),
    Hashbrowns("Hashbrowns", "Hashbrowns", "Hashbrowns! This one's simple, but that's a good thing!", FoodRecipes.Hashbrowns),
    MakiRoll("Maki Roll", "Maki_Roll", "Maki Roll! The delicate flavor of the ocean, sealed within a pillowy cloud of rice.", FoodRecipes.MakiRoll),
    MinersTreat("Miner's Treat", "Miner%27s_Treat", "Miner's Treat boosts mining.", FoodRecipes.MinersTreat),
    Omelet("Omelet", "Omelet", "Omelet! This is such a simple dish, but so often done incorrectly!", FoodRecipes.Omelet),
    Pancakes("Pancakes", "Pancakes", "Pancakes! Sometimes I get carried away... but there's something comforting about a simple pancake.", FoodRecipes.Pancakes),
    Pizza("Pizza", "Pizza", "Pizza! There's a reason pizza is a timeless culinary classic.", FoodRecipes.Pizza),
    PumpkinPie("Pumpkin Pie", "Pumpkin_Pie", "Pumpkin Pie! In my house, it's a tradition to eat pumpkin pie during the Feast of the Winter Star.", FoodRecipes.PumpkinPie),
    RedPlate("Red Plate", "Red_Plate", "Red Plate! Vegetable-rich dish for stamina boost.", FoodRecipes.RedPlate),
    SalmonDinner("Salmon Dinner", "Salmon_Dinner", "Salmon Dinner boosts fishing.", FoodRecipes.SalmonDinner),
    SeafoamPudding("Seafoam Pudding", "Seafoam_Pudding", "Seafoam Pudding maximizes fishing skill.", FoodRecipes.SeafoamPudding),
    SurvivalBurger("Survival Burger", "Survival_Burger", "Survival Burger boosts foraging.", FoodRecipes.SurvivalBurger),
    Tortilla("Tortilla", "Tortilla", "Tortillas! How many of you are gnawing on a convenience-burrito?", FoodRecipes.Tortilla),
    TripleShotEspresso("Triple Shot Espresso", "Triple_Shot_Espresso", "Triple Shot Espresso generates massive speed for a full day.", FoodRecipes.TripleShotEspresso),
    VegetableMedley("Vegetable Medley", "Vegetable_Medley", "Vegetable Medley restores health.", FoodRecipes.VegetableMedley);

    private final String name;
    private final String atlasKey;
    private final String description;
    private final FoodRecipes recipe;

    Cookings(String name, String atlasKey, String description, FoodRecipes recipe) {
        this.name = name;
        this.atlasKey = atlasKey;
        this.description = description;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public String getDescription() {
        return description;
    }

    public FoodRecipes getRecipe() {
        return recipe;
    }

    public static Cookings getFoodByName(String name) {
        for(Cookings cooking : Cookings.values()) {
            if(cooking.getName().equals(name))
                return cooking;
        }
        return null;
    }
}
