package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.ItemStack;
import phi.ap.model.items.products.Crop;
import phi.ap.model.items.products.*;

import java.util.ArrayList;
import java.util.Arrays;

public enum FoodTypes {
    // Artisian FoodTypes :
    Mayonnaise("Mayonnaise", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(50), 190, 3),

    DuckMayonnaise("DuckMayonnaise", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.DuckEgg), 1))),
            new Eatable(75), 37, 3),

    DinosaurMayonnaise("DinosaurMayonnaise", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.DinosaurEgg), 1))),
            new Eatable(125), 800, 3),

    Oil("Oil", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Corn), 1))),
            new Eatable(13), 100, 6),
    TruffleOil("TruffleOil", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Truffle), 1))),
            new Eatable(38), 1065, 6),

    Pickles("Pickles", null,
            null, 0, 6),

    Jelly("Jelly", null,
            null, 0, 3 * 24),

    SmokedFish("SmokedFish", null,
            null, 0, 1),

    Beer("FriedEgg", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Wheat), 1))),
            new Eatable(50), 200, 24),

    Vinegar("FriedEgg", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Food(1, 1, "Rice", 10, new Eatable(5)), 1))),
            new Eatable(13), 100, 10),

    Coffee("FriedEgg", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.CoffeeBean), 5))),
            new Eatable(75), 150, 2),

    Juice("Juice", null, null, 0, 4 * 24),

    Mead("Mead", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Food(1, 1, "Honey", 35, new Eatable(75)), 1))),
    new Eatable(100), 300, 10),

    PaleAle("PaleAle", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Hops), 1))),
            new Eatable(50), 300, 3 * 24),

    Wine("Wine", null, null, 0, 7 * 24),

    Honey("Honey", null, new Eatable(75), 350, 4 * 24),

    Cheese("Cheese", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
            new Eatable(100), 230, 3),

    CheeseWithLargeMilk("Cheese", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.LargeMilk), 1))),
            new Eatable(100), 345, 3),

    GoatCheese("Cheese", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.GoatMilk), 1))),
            new Eatable(100), 400, 3),

    GoatCheeseWithLargeMilk("Cheese", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.LargeGoatMilk), 1))),
            new Eatable(100), 600, 3),

    DriedMushrooms("DriedMushrooms", null, null, 0, 24),

    DriedFruit("DriedFruit", null, null, 0, 24),

    Raisins("Raisins", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new Fruit(1, 1, FruitTypes.Cherry), 5))),
    new Eatable(125), 600),

    // buying foods.
    Hay("Hay",null, null, 10),
    JojaCola("JojaCola", null, new Eatable(5), 10),
    Sugar("Sugar", null, new Eatable(5), 10),
    WheatFlour("WheatFlour", null, new Eatable(5), 10),
    Rice("Rice", null, new Eatable(5), 10),

    // cooking recipes

    FriedEgg("FriedEgg", new ArrayList<ItemStack>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(50), 35, null, null),
    BakedFish("BakedFish", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Sardine), 1),
            new ItemStack(new Fish(1, 1, FishTypes.Salmon), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Wheat), 1))),
            new Eatable(75), 100, null, null),

    Salad("Salad", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, ForagingCropsTypes.Leek), 1),
            new ItemStack(new Crop(1, 1, ForagingCropsTypes.Dandelion), 1))),
            new Eatable(113), 110, null, null),

    Omelet("Omelet", new ArrayList<>(Arrays.asList(
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
            new Eatable(100), 125, null, null),

    PumpkinPie("PumpkinPie", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Pumpkin), 1),
            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1),
            new ItemStack(new Food(1, 1, "Sugar", 10, new Eatable(5)), 1))),
            new Eatable(225), 385, null, null),
    Spaghetti("Spaghetti", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Tomato), 1))),
            new Eatable(75), 120, null, null),

    Pizza("Pizza", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Tomato), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Cheese), 1))),
            new Eatable(150), 300, null, null),

    Tortilla("Tortilla", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Corn), 1))),
            new Eatable(50), 50, null, null),

    MakiRoll("MakiRoll", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Angler), 1),
            new ItemStack(new Food(1, 1, "Rice", 10, new Eatable(5)), 1))),
            new Eatable(100), 220, null, null),

    TripleShotEspresso("TripleShotEspresso", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "Coffee", 150, new Eatable(5)), 3))),
            new Eatable(200), 450, null, null),

    Cookie("Cookie", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
            new ItemStack(new Food(1, 1, "Sugar", 10, new Eatable(5)), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(90), 140, null, null),

    HashBrowns("HashBrowns", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Potato), 1),
            new ItemStack(new Food(1, 1, "Oil", 10, new Eatable(5)), 1))),
            new Eatable(90), 120, null, null),

    Pancakes("Pancakes", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
            new Eatable(90), 80, null, null),
    FruitSalad("FruitSalad", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Blueberry), 1),
            new ItemStack(new Fruit(1, 1, FruitTypes.Apricot), 1))),
            new Eatable(263), 450, null, null),

    RedPlate("RedPlate", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.RedCabbage), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Radish), 1))),
            new Eatable(240), 400, null, null),

    Bread("Bread", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1))),
            new Eatable(50), 60, null, null),

    SalmonDinner("SalmonDinner", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Salmon), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Amaranth), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Kale), 1))),
            new Eatable(125), 300, null, null),

    VegetableMedley("VegetableMedley", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Tomato), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Beet), 1))),
            new Eatable(165), 120, null, null),

    FarmersLunch("FarmersLunch", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "Omelet", 125, new Eatable(100)), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Parsnip), 1))),
            new Eatable(200), 150, null, null),

    SurvivalBurger("SurvivalBurger", new ArrayList<>(Arrays.asList(
            new ItemStack(new Food(1, 1, "Bread", 60, new Eatable(50)), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Carrot), 1),
            new ItemStack(new Crop(1, 1, CropsTypes.Eggplant), 1))),
            new Eatable(125), 180, null, null),

    DishOTheSea("DishOTheSea", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Sardine), 2),
            new ItemStack(new Food(1, 1, "HashBrowns", 120, new Eatable(90)), 1))),
            new Eatable(150), 220, null, null),
    SeafoamPudding("SeafoamPudding", new ArrayList<>(Arrays.asList(
            new ItemStack(new Fish(1, 1, FishTypes.Flounder), 1),
            new ItemStack(new Fish(1, 1, FishTypes.MidnightCarp), 1))),
            new Eatable(175), 300, null, null),

    MinersTreat("MinersTreat", new ArrayList<>(Arrays.asList(
            new ItemStack(new Crop(1, 1, CropsTypes.Carrot), 2),
            new ItemStack(new Food(1, 1, "Sugar", 10, new Eatable(5)), 1),
            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
            new Eatable(125), 200, null, null);
    // making foods.
    private ArrayList<ItemStack> ingredients;
    private Recipe recipe;
    private String name;
    private Eatable eatable;
    private int sellPrice;
    private AbilityType abilityType;
    private Integer Buffhour;
    private Integer ProcessingTime;

    private FoodTypes(String name, ArrayList<ItemStack> ingredients, Eatable eatable, int sellPrice, AbilityType abilityType, Integer hour) {
        this.ingredients = ingredients;
        recipe = new Recipe(1, 1, ingredients, new ItemStack(new Food(1, 1, name, sellPrice, eatable), 1), RecipeTypes.valueOf(name));
        this.eatable = eatable;
        this.sellPrice = sellPrice;
        this.name = name;
        this.abilityType = abilityType;
        this.Buffhour = hour;
    }
    private FoodTypes(String name, ArrayList<ItemStack> ingredients, Eatable eatable, int sellPrice) {
        this.ingredients = ingredients;
        this.eatable = eatable;
        this.sellPrice = sellPrice;
        this.name = name;
    }
    private FoodTypes(String name, ArrayList<ItemStack> ingredients, Eatable eatable, int sellPrice, Integer processingTime) {
        this.ingredients = ingredients;
        recipe = new Recipe(1, 1, ingredients, new ItemStack(new Food(1, 1, name, sellPrice, eatable), 1), RecipeTypes.valueOf(name));
        this.eatable = eatable;
        this.sellPrice = sellPrice;
        this.name = name;
        this.ProcessingTime = processingTime;
    }

    public Integer getProcessingTime() {
        return ProcessingTime;
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
        return Buffhour;
    }
}