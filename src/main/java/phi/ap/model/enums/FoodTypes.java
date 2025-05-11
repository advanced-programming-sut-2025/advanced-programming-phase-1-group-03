package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.ItemStack;
import phi.ap.model.items.Crop;
import phi.ap.model.items.products.*;

import java.util.ArrayList;
import java.util.Arrays;

public enum FoodTypes {
    // buying foods.
    Hay("Hay",null, null, 0),
    Beer("Beer", null, null, 0),
    Coffee("Coffee", null, null, 0),
    JojaCola("JojaCola", null, null, 0),
    Sugar("Sugar", null, null, 0),
    WheatFlour("WheatFlour", null, null, 0),
    Rice("Rice", null, null, 0),
    Vinegar("Vinegar", null, null, 0),
    Oil("Oil", null, null, 0),
    FriedEgg("FriedEgg", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(50), 35, null, null),
    BakedFish("BakedFish", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Sardine), 1),
            new ItemStack(new Fish(1, 1, FishTypes.Salmon), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.WHEAT), 1))),
            new Eatable(75), 100, null, null),

    Salad("Salad", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, ForagingCropsTypes.LEEK), 1),
            new ItemStack(new Crop(1, 1, ForagingCropsTypes.DANDELION), 1))),
            new Eatable(113), 110, null, null),

    Omelet("Omelet", new ArrayList<>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
            new Eatable(100), 125, null, null),

    PumpkinPie("PumpkinPie", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.PUMPKIN), 1),
            new ItemStack(new Food(1, 1, FoodTypes.WheatFlour), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1),
            new ItemStack(new Food(1, 1, FoodTypes.Sugar), 1))),
            new Eatable(225), 385, null, null),
    Spaghetti("Spaghetti", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.WheatFlour), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.TOMATO), 1))),
            new Eatable(75), 120, null, null),

    Pizza("Pizza", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.WheatFlour), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.TOMATO), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Cheese), 1))),
            new Eatable(150), 300, null, null),

    Tortilla("Tortilla", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Corn), 1))),
            new Eatable(50), 50, null, null),

    MakiRoll("MakiRoll", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Angler), 1),
            new ItemStack(new Food(1, 1, FoodTypes.Rice), 1))),
            new Eatable(100), 220, null, null),

    TripleShotEspresso("TripleShotEspresso", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.Coffee), 3))),
            new Eatable(200), 450, null, null),

    Cookie("Cookie", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.WheatFlour), 1),
            new ItemStack(new Food(1, 1, FoodTypes.Sugar), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(90), 140, null, null),

    HashBrowns("HashBrowns", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.POTATO), 1),
            new ItemStack(new Food(1, 1, FoodTypes.Oil), 1))),
            new Eatable(90), 120, null, null),

    Pancakes("Pancakes", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.WheatFlour), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(90), 80, null, null),
    FruitSalad("FruitSalad", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.BLUEBERRY), 1),
            new ItemStack(new Fruit(1, 1, FruitTypes.Apricot), 1))),
            new Eatable(263), 450, null, null),

    RedPlate("RedPlate", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.RED_CABBAGE), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.RADISH), 1))),
            new Eatable(240), 400, null, null),

    Bread("Bread", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.WheatFlour), 1))),
            new Eatable(50), 60, null, null),
    SalmonDinner("SalmonDinner", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Salmon), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.AMARANTH), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.KALE), 1))),
            new Eatable(125), 300, null, null),

    VegetableMedley("VegetableMedley", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.TOMATO), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.BEET), 1))),
            new Eatable(165), 120, null, null),

    FarmersLunch("FarmersLunch", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.Omelet), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.PARSNIP), 1))),
            new Eatable(200), 150, null, null),

    SurvivalBurger("SurvivalBurger", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, FoodTypes.Bread), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.CARROT), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.EGGPLANT), 1))),
            new Eatable(125), 180, null, null),

    DishOTheSea("DishOTheSea", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Sardine), 2),
            new ItemStack(new Food(1, 1, FoodTypes.HashBrowns), 1))),
            new Eatable(150), 220, null, null),
    SeafoamPudding("SeafoamPudding", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Flounder), 1),
            new ItemStack(new Fish(1, 1, FishTypes.MidnightCarp), 1))),
            new Eatable(175), 300, null, null),

    MinersTreat("MinersTreat", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.CARROT), 2),
            new ItemStack(new Food(1, 1, FoodTypes.Sugar), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
            new Eatable(125), 200, null, null);
    // making foods.
    private ArrayList<ItemStack> ingredients;
    private Recipe recipe;


    private String name;
    private Eatable eatable;
    private int sellPrice;
    private AbilityType abilityType;
    private Integer hour;
    private FoodTypes(String name, ArrayList<ItemStack> ingredients, Eatable eatable, int sellPrice, AbilityType abilityType, Integer hour) {
        this.ingredients = ingredients;
        recipe = new Recipe(1, 1, ingredients, new ItemStack(new Food(1, 1, FoodTypes.valueOf(name)), 1), RecipeTypes.valueOf(name));
        this.eatable = eatable;
        this.sellPrice = sellPrice;
        this.name = name;
        this.abilityType = abilityType;
        this.hour = hour;
    }
    private FoodTypes(String name, ArrayList<ItemStack> ingredients, Eatable eatable, int sellPrice) {
        this.ingredients = ingredients;
        this.eatable = eatable;
        this.sellPrice = sellPrice;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public ArrayList<ItemStack> getIngredients() {
        return ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Eatable getEatable() {
        return eatable;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public Integer getHour() {
        return hour;
    }
}