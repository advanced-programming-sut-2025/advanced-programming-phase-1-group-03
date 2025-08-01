package com.ap.model;

public enum Foods {

    Salad("Salad", 113),

    Beer("Beer",50),

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

    Cookie("Cookie", 90);
    ;

    // Artisian FoodTypes :
//    Mayonnaise("Mayonnaise", 50),
//
//    DuckMayonnaise("DuckMayonnaise",75),
//
//    DinosaurMayonnaise("DinosaurMayonnaise",125),
//
//    Oil("Oil", 13),
//
//    TruffleOil("TruffleOil",38),

 //   Pickles("Pickles", null,
 //           null, 0, 6),

  //  Jelly("Jelly", null,
  //          null, 0, 3 * 14),

 //   SmokedFish("SmokedFish", null,
 //           null, 0, 1),


//
//    Vinegar("Vinegar", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "Rice", 10, new Eatable(5)), 1))),
//            new Eatable(13), 100, 10),
//
//    Coffee("Coffee", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.CoffeeBean), 5))),
//            new Eatable(75), 150, 2),
//
//    Juice("Juice", null, null, 0, 4 * 14),
//
//    Mead("Mead", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "Honey", 35, new Eatable(75)), 1))),
//            new Eatable(100), 300, 10),
//
//    PaleAle("PaleAle", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Hops), 1))),
//            new Eatable(50), 300, 3 * 24),
//
//    Wine("Wine", null, null, 0, 7 * 14),
//
//    Honey("Honey", null, new Eatable(75), 350, 4 * 14),
//
//    Cheese("Cheese", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
//            new Eatable(100), 230, 3),
//
//    CheeseWithLargeMilk("Cheese", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.LargeMilk), 1))),
//            new Eatable(100), 345, 3),
//
//    GoatCheese("Cheese", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.GoatMilk), 1))),
//            new Eatable(100), 400, 3),
//
//    GoatCheeseWithLargeMilk("Cheese", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.LargeGoatMilk), 1))),
//            new Eatable(100), 600, 3),
//
//    DriedMushrooms("DriedMushrooms", null, null, 0, 14),
//
//    DriedFruit("DriedFruit", null, null, 0, 14),
//
//    Raisins("Raisins", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new Fruit(1, 1, FruitTypes.Cherry), 5))),
//            new Eatable(125), 600),

    // buying foods.
//    Hay("Hay",null, null, 10),
//    JojaCola("JojaCola", null, new Eatable(5), 10),
//    Sugar("Sugar", null, new Eatable(5), 10),
//    WheatFlour("WheatFlour", null, new Eatable(5), 10),
//    Rice("Rice", null, new Eatable(5), 10),

    // cooking recipes
//
//    FriedEgg("FriedEgg", new ArrayList<ItemStack>(Arrays.asList(
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
//            new Eatable(50), 35, null, null),
//    BakedFish("BakedFish", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Fish(1, 1, FishTypes.Sardine), 1),
//            new ItemStack(new Fish(1, 1, FishTypes.Salmon), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Wheat), 1))),
//            new Eatable(75), 100, null, null),
//
//
//    Omelet("Omelet", new ArrayList<>(Arrays.asList(
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1),
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
//            new Eatable(100), 125, null, null),
//
//    PumpkinPie("PumpkinPie", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Pumpkin), 1),
//            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1),
//            new ItemStack(new Food(1, 1, "Sugar", 10, new Eatable(5)), 1))),
//            new Eatable(225), 385, null, null),
//
//    Pizza("Pizza", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Tomato), 1),
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Cheese), 1))),
//            new Eatable(150), 300, null, null),
//
//    Tortilla("Tortilla", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Corn), 1))),
//            new Eatable(50), 50, null, null),
//
//    MakiRoll("MakiRoll", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Fish(1, 1, FishTypes.Angler), 1),
//            new ItemStack(new Food(1, 1, "Rice", 10, new Eatable(5)), 1))),
//            new Eatable(100), 220, null, null),
//
//    TripleShotEspresso("TripleShotEspresso", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "Coffee", 150, new Eatable(5)), 3))),
//            new Eatable(200), 450, new Buff(5, 100), null),
//
//    Cookie("Cookie", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
//            new ItemStack(new Food(1, 1, "Sugar", 10, new Eatable(5)), 1),
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
//            new Eatable(90), 140, null, null),
//
//    HashBrowns("HashBrowns", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Potato), 1),
//            new ItemStack(new Food(1, 1, "Oil", 10, new Eatable(5)), 1))),
//            new Eatable(90), 120, new Buff(5, AbilityType.Farming), null),
//
//    Pancakes("Pancakes", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "WheatFlour", 10, new Eatable(5)), 1),
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))),
//            new Eatable(90), 80, new Buff(11, AbilityType.Foraging), null),
//    FruitSalad("FruitSalad", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Blueberry), 1),
//            new ItemStack(new Fruit(1, 1, FruitTypes.Apricot), 1))),
//            new Eatable(263), 450, null, null),
//
//    RedPlate("RedPlate", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.RedCabbage), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Radish), 1))),
//            new Eatable(240), 400,  new Buff(3, 50), null),
//
//
//    SalmonDinner("SalmonDinner", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Fish(1, 1, FishTypes.Salmon), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Amaranth), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Kale), 1))),
//            new Eatable(125), 300, null, null),
//
//    VegetableMedley("VegetableMedley", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Tomato), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Beet), 1))),
//            new Eatable(165), 120, null, 2),
//
//    FarmersLunch("FarmersLunch", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "Omelet", 125, new Eatable(100)), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Parsnip), 1))),
//            new Eatable(200), 150, new Buff(5, AbilityType.Farming), 1),
//
//    SurvivalBurger("SurvivalBurger", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Food(1, 1, "Bread", 60, new Eatable(50)), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Carrot), 1),
//            new ItemStack(new Crop(1, 1, CropsTypes.Eggplant), 1))),
//            new Eatable(125), 180, new Buff(5, AbilityType.Foraging), 5),
//
//    DishOTheSea("DishOTheSea", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Fish(1, 1, FishTypes.Sardine), 2),
//            new ItemStack(new Food(1, 1, "HashBrowns", 120, new Eatable(90)), 1))),
//            new Eatable(150), 220, new Buff(5, AbilityType.Fishing), 2),
//    SeafoamPudding("SeafoamPudding", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Fish(1, 1, FishTypes.Flounder), 1),
//            new ItemStack(new Fish(1, 1, FishTypes.MidnightCarp), 1))),
//            new Eatable(175), 300, new Buff(10, AbilityType.Fishing), 3),
//
//    MinersTreat("MinersTreat", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, CropsTypes.Carrot), 2),
//            new ItemStack(new Food(1, 1, "Sugar", 10, new Eatable(5)), 1),
//            new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Milk), 1))),
//            new Eatable(125), 200, new Buff(5, AbilityType.Extraction), 1));

    // making foods.
//    private ArrayList<ItemStack> ingredients;
//    private Recipe recipe;
//    private Eatable eatable;
//    private int sellPrice;
//    private AbilityType abilityType;
//    private Integer level = null;
//    private Integer Buffhour;
//    private Buff foodBuff = null;
//    private Integer ProcessingTime;

    private String name;
    private Integer energy;

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


    //    public Integer getLevel() {
//        return level;
//    }
//
//    public Integer getProcessingTime() {
//        return ProcessingTime;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public ArrayList<ItemStack> getIngredients() {
//        return ingredients;
//    }
//
//    public Recipe getRecipe() {
//        return recipe;
//    }
//
//    public Eatable getEatable() {
//        return eatable;
//    }
//
//    public int getSellPrice() {
//        return sellPrice;
//    }
//
//    public AbilityType getAbilityType() {
//        return abilityType;
//    }
//
//    public Integer getHour() {
//        return Buffhour;
//    }
//
//    public void setFoodBuff(Buff foddBuff) {
//        this.foodBuff = foddBuff;
//    }
//
//    public Buff getFoddBuff() {
//        return this.foodBuff;
//    }
//
//    public static FoodTypes getType(String name) {
//        FoodTypes foodType;
//        try {
//            foodType = FoodTypes.valueOf(name);
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//        return foodType;
//    }
}
