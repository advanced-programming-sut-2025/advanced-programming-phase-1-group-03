package com.ap.model;

public enum FoodRecipes {
    Hashbrowns(Foods.HashBrowns),
    Omelet(Foods.Omelet),
    Pancakes(Foods.Pancakes),
    Bread(Foods.Bread),
    Tortilla(Foods.Tortilla),
    Pizza(Foods.Pizza),
    MakiRoll(Foods.MakiRoll),
    TripleShotEspresso(Foods.TripleShotEspresso),
    Cookie(Foods.Cookie),
    BakedFish(Foods.BakedFish),
    DishOTheSea(Foods.DishOTheSea),
    FarmersLunch(Foods.FarmersLunch),
    FriedEgg(Foods.FriedEgg),
    FruitSalad(Foods.FruitSalad),
    MinersTreat(Foods.MinersTreat),
    PumpkinPie(Foods.PumpkinPie),
    RedPlate(Foods.RedPlate),
    SalmonDinner(Foods.SalmonDinner),
    SeafoamPudding(Foods.SeafoamPudding),
    SurvivalBurger(Foods.SurvivalBurger),
    VegetableMedley(Foods.VegetableMedley);

    private final Foods food;

    FoodRecipes(Foods food) {
        this.food = food;
    }

    public Foods getFood() {
        return food;
    }
}
