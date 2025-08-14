package com.ap.model;

public enum Foods {

    Salad("Salad", 113),
    Beer("Beer", 50),
    Bread("Bread", 50),
    Spaghetti("Spaghetti", 75),
    Pizza("Pizza", 150),
    Coffee("Coffee", 75),
    HashBrowns("HashBrowns", 90),
    Omelet("Omelet", 100),
    Pancakes("Pancakes", 90),
    Tortilla("Tortilla", 50),
    MakiRoll("MakiRoll", 100),
    TripleShotEspresso("TripleShotEspresso", 200),
    Cookie("Cookie", 90),
    BakedFish("BakedFish", 120),
    DishOTheSea("DishOTheSea", 130),
    FarmersLunch("FarmersLunch", 110),
    FriedEgg("FriedEgg", 60),
    FruitSalad("FruitSalad", 80),
    MinersTreat("MinersTreat", 120),
    PumpkinPie("PumpkinPie", 100),
    RedPlate("RedPlate", 70),
    SalmonDinner("SalmonDinner", 130),
    SeafoamPudding("SeafoamPudding", 150),
    SurvivalBurger("SurvivalBurger", 140),
    VegetableMedley("VegetableMedley", 90);

    private final String name;
    private final Integer energy;

    Foods(String name, Integer energy) {
        this.name = name;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public Integer getEnergy() {
        return energy;
    }

    public static Foods getFoodByName(String name) {
        for(Foods food : Foods.values()) {
            if(food.getName().equals(name))
                return food;
        }
        return null;
    }
}
