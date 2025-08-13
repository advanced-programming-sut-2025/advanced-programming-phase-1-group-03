package com.ap.model;

public enum Cookings {
    Pizza("Pizza", "Pizza", "Pizza! There's a reason pizza is a timeless culinary classic.", FoodRecipes.Pizza),
    Hashbrowns("Hashbrowns", "Hashbrowns", "Hashbrowns! This one's simple, but that's a good thing!", FoodRecipes.Hashbrowns),

    TripleShotEspresso("Triple Shot Espresso", "Triple_Shot_Espresso", "Triple Shot Espresso generates massive speed for a full day.", FoodRecipes.TripleShotEspersso),
    Makiroll("Maki Roll", "Maki_Roll", "Maki Roll! The delicate flavor of the ocean, sealed within a pillowy cloud of rice.", FoodRecipes.Makiroll),
    Bread("Bread", "Bread", "Bread! Breadmaking can be a very complex form of art, but I'll make it simple for you.", FoodRecipes.Bread),
    Tortilla("Tortilla", "Tortilla", "Tortillas! How many of you are gnawing on a convenience‑burrito?", FoodRecipes.Tortilla),
    Cookie("Cookie", "Cookie", "Cookie! Sweet treat that boosts morale.", FoodRecipes.Cookie),
    Omelet("Omelet", "Omelet", "Omelet! This is such a simple dish, but so often done incorrectly!", FoodRecipes.Omelet),
    Pancakes("Pancakes", "Pancakes", "Pancakes! Sometimes I get carried away... but there's something comforting about a simple pancake.", FoodRecipes.Pancakes),
    ;
//
//    Spaghetti("Spaghetti", "Spaghetti", "Spaghetti restores energy and health."),
//    BakedFish("Baked Fish", "Baked_Fish", "Baked fish on a bed of herbs."),
//    Salad("Salad", "Salad", "Salad! No bland salad here—fresh lemony greens for energy."),
//
//    DishTheSea("Dish O' The Sea", "Dish_O%27_The_Sea", "Dish O' The Sea boosts fishing skill."),
//
//    FarmerLunch("Farmer's Lunch", "Farmer%27s_Lunch", "Farmer's Lunch boosts farming."),
//
//    FiredEgg("Fried Egg", "Fried_Egg", "Fried Egg is known by the player upon starting a new save file."),
//
//    FruitSalad("Fruit Salad", "Fruit_Salad", "Fruit Salad! Here's a healthy and delicious treat to brighten up your day."),
//
//
//
//    MinerTreat("Miner's Treat", "Miner%27s_Treat", "Miners Treat boosts mining."),
//
//
//    PumpkinPie("Pumpkin Pie", "Pumpkin_Pie", "Pumpkin Pie! In my house, it's a tradition to eat pumpkin pie during the Feast of the Winter Star."),
//
//    RedPlate("Red Plate", "Red_Plate", "Red Plate! Vegetable‑rich dish for stamina boost."),
//
//
//    SalmonDinner("Salmon Dinner", "Salmon_Dinner", "Salmon Dinner boosts fishing."),
//
//    SeafoamPudding("Seafoam Pudding", "Seafoam_Pudding", "Seafoam Pudding maximizes fishing skill."),
//
//
//    SurvivalBurger("Survival Burger", "Survival_Burger", "Survival Burger boosts foraging."),
//
//
//
//    VegtableMedley("Vegetable Medley", "Vegetable_Medley", "Vegetable Medley restores health.");

    private String name;
    private String atlasKey;
    private String description;
    private FoodRecipes recipe;

    Cookings(String name, String atlasKey, String description, FoodRecipes recipe) {
        this.name = name;
        this.atlasKey = atlasKey;
        this.description = description;
        this.recipe = recipe;
    }
}
