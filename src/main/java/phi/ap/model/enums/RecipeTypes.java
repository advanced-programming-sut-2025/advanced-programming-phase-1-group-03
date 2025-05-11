package phi.ap.model.enums;

public enum RecipeTypes {
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

    Dehydrator("Dehydrator"),
    GrassStarter("GrassStarter")
    ;
    private final String name;
    RecipeTypes(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
