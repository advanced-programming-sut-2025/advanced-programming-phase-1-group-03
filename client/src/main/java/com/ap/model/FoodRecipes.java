package com.ap.model;

public enum FoodRecipes {
    Hashbrowns(Foods.HashBrowns),
    Omelet(Foods.Omelet),
    Pancakes(Foods.Pancakes),
    Bread(Foods.Bread),
    Tortilla(Foods.Tortilla),
    Pizza(Foods.Pizza),
    Makiroll(Foods.MakiRoll),
    TripleShotEspersso(Foods.TripleShotEspresso),
    Cookie(Foods.Cookie);

    private final Foods food;
    FoodRecipes(Foods food) {
        this.food = food;
    }

    public Foods getFood() {
        return food;
    }
}
