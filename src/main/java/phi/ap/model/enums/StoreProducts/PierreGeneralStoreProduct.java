package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.*;
import phi.ap.model.enums.Time. Season;
import phi.ap.model.items.Item;
import phi.ap.model.items.Sapling;
import phi.ap.model.items.Seed;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.products.Product;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.products.Soil;
import phi.ap.model.items.tools.Backpack;

public enum PierreGeneralStoreProduct implements StoreItemProducer {
    LargePack("Large Pack", "Unlocks the 2nd row of inventory (12 more slots, total 24).", 2000,
            2000, 1, new Backpack(1),  Season.Spring),
    DeluxPack("Delux Pack", "Unlocks the 3rd row of inventory (infinite slots).", 10000,
            10000
            , 1, new Backpack(2),  Season.Spring),
    // All Season Products

    Rice("Rice", "A basic grain often served under vegetables.", 200, null,
            Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Rice), null),
    WheatFlour("Wheat Flour", "A common cooking ingredient made from crushed wheat seeds.", 100,
            null, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.WheatFlour), null),

    Bouquet("Bouquet", "A gift that shows your romantic interest.", 1000,null, 2, new Product(ProductNames.Bouquet), null),
    WeddingRing("Wedding Ring", "It's used to ask for another farmer's hand in marriage.", 10000,null, 2, new Product(ProductNames.WeddingRing), null),

    DehydratorRecipe("Dehydrator Recipe", "A recipe to make Dehydrator", 10000, null,
            1, new Recipe(null, null, RecipeTypes.Dehydrator), null),
    GrassStarterRecipe("Grass Starter Recipe", "A recipe to make Grass Starter", 1000,
            null, 1, new Recipe(null, null,
            RecipeTypes.GrassStarter), null),
    Sugar("Sugar", "Adds sweetness to pastries and candies. Too much can be unhealthy.", 100,
            null, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Sugar), null),
    Oil("Oil", "All purpose cooking oil.", 200, null, Integer.MAX_VALUE,
            new Food(1, 1, FoodTypes.Oil), null),
    Vinegar("Vinegar", "An aged fermented liquid used in many cooking recipes.", 200,
            null, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Vinegar), null),
    DeluxeRetainingSoil("Deluxe Retaining Soil", "This soil has a 100% chance of staying watered overnight. Mix into tilled soil.", 150,null, Integer.MAX_VALUE, new Soil(1, 1, SoilTypes.DeluxeRetaining), null),
    GrassStarter("Grass Starter", "Place this on your farm to start a new patch of grass.", 100,
            null, Integer.MAX_VALUE, new Soil(1, 1, SoilTypes.GrassStarter), null),
    SpeedGro("Speed-Gro", "Makes the plants grow 1 day earlier.", 100,null,
            Integer.MAX_VALUE, new Soil(1, 1, SoilTypes.SpeedGro), null),

    AppleSapling("Apple Sapling", "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall.",
            4000,null, Integer.MAX_VALUE, new Sapling(1, 1,
            SaplingTypes.AppleSapling), null),
    ApricotSapling("Apricot Sapling", "Takes 28 days to produce a mature Apricot tree. Bears fruit " +
            "in the spring.", 2000,null, Integer.MAX_VALUE, new Sapling(1, 1,
            SaplingTypes.ApricotSapling), null),
    CherrySapling("Cherry Sapling", "Takes 28 days to produce a mature Cherry tree. Bears fruit in " +
            "the spring.", 3400,null, Integer.MAX_VALUE, new Sapling(1, 1,
            SaplingTypes.CherrySapling), null),
    OrangeSapling("Orange Sapling", "Takes 28 days to produce a mature Orange tree. Bears fruit " +
            "in the summer.", 4000,null, Integer.MAX_VALUE, new Sapling(1, 1,
            SaplingTypes.OrangeSapling), null),
    PeachSapling("Peach Sapling", "Takes 28 days to produce a mature Peach tree. Bears fruit " +
            "in the summer.", 6000,null, Integer.MAX_VALUE, new Sapling(1, 1,
            SaplingTypes.PeachSapling), null),
    PomegranateSapling("Pomegranate Sapling", "Takes 28 days to produce a mature Pomegranate tree. " +
            "Bears fruit in the fall.", 6000,null, Integer.MAX_VALUE,
            new Sapling(1, 1, SaplingTypes.PomegranateSapling), null),

    BasicRetainingSoil("Basic Retaining Soil", "This soil has a chance of staying watered overnight. " +
            "Mix into tilled soil.", 100,null, Integer.MAX_VALUE, new Soil(1, 1,
            SoilTypes.BasicRetaining), null),
    QualityRetainingSoil("Quality Retaining Soil", "This soil has a good chance of staying watered o" +
            "vernight. Mix into tilled soil.", 150,null, Integer.MAX_VALUE,
            new Soil(1, 1, SoilTypes.QualityRetaining), null),

    // Spring products
    ParsnipSeeds("Parsnip Seeds", "Plant these in the spring. Takes 4 days to mature.", 20,
            30, 5, new Seed(1, 1, SeedTypes.ParsnipSeeds),  Season.Spring),
    BeanStarter("Bean Starter", "Plant these in the spring. Takes 10 days to mature, but keeps producing " +
            "after that. Grows on a trellis.", 60, 90, 5, new Seed(1, 1,
            SeedTypes.BeanStarter),  Season.Spring),
    CauliflowerSeeds("Cauliflower Seeds", "Plant these in the spring. Takes 12 days to produce a large " +
            "cauliflower.", 80, 120, 5, new Seed(1, 1,
            SeedTypes.CauliflowerSeeds),  Season.Spring),
    PotatoSeeds("Potato Seeds", "Plant these in the spring. Takes 6 days to mature, and has a chance of" +
            " yielding multiple potatoes at harvest.", 50, 75, 5,
            new Seed(1, 1, SeedTypes.PotatoSeeds),  Season.Spring),
    TulipBulb("Tulip Bulb", "Plant in spring. Takes 6 days to produce a colorful flower. Assorted " +
            "colors.", 20, 30, 5, new Seed(1, 1,
            SeedTypes.TulipBulb),  Season.Spring),
    KaleSeeds("Kale Seeds", "Plant these in the spring. Takes 6 days to mature. Harvest with the " +
            "scythe.", 70, 105, 5, new Seed(1, 1,
            SeedTypes.KaleSeeds),  Season.Spring),
    JazzSeeds("Jazz Seeds", "Plant in spring. Takes 7 days to produce a blue puffball flower.",
            30, 45, 5, new Seed(1, 1, SeedTypes.JazzSeeds),  Season.Spring),
    GarlicSeeds("Garlic Seeds", "Plant these in the spring. Takes 4 days to mature.",
            40, 60, 5, new Seed(1, 1, SeedTypes.GarlicSeeds),  Season.Spring),
    RiceShoot("Rice Shoot", "Plant these in the spring. Takes 8 days to mature. Grows faster if planted" +
            " near a body of water. Harvest with the scythe.", 40, 60, 5,
            new Seed(1, 1, SeedTypes.RiceShoot),  Season.Spring),

    // Summer products

    MelonSeeds("Melon Seeds", "Plant these in the summer. Takes 12 days to mature.",
            80, 120, 5, new Seed(1, 1, SeedTypes.MelonSeeds),  Season.Summer),
    TomatoSeeds("Tomato Seeds", "Plant these in the summer. Takes 11 days to mature, and " +
            "continues to produce after first harvest.", 50, 75, 5,
            new Seed(1, 1, SeedTypes.TomatoSeeds),  Season.Summer),
    BlueberrySeeds("Blueberry Seeds", "Plant these in the summer. Takes 13 days to mature, and " +
            "continues to produce after first harvest.", 80, 120, 5,
            new Seed(1, 1, SeedTypes.BlueberrySeeds),  Season.Summer),
    PepperSeeds("Pepper Seeds", "Plant these in the summer. Takes 5 days to mature, and continues " +
            "to produce after first harvest.", 40, 60, 5,
            new Seed(1, 1, SeedTypes.PepperSeeds),  Season.Summer),
    WheatSeeds("Wheat Seeds", "Plant these in the summer or fall. Takes 4 days to mature. Harvest with" +
            " the scythe.", 10, 15, 5, new Seed(1, 1,
            SeedTypes.WheatSeeds),  Season.Summer),
    RadishSeeds("Radish Seeds", "Plant these in the summer. Takes 6 days to mature.", 40,
            60, 5, new Seed(1, 1, SeedTypes.RadishSeeds),  Season.Summer),
    PoppySeeds("Poppy Seeds", "Plant in summer. Produces a bright red flower in 7 days.", 100,
            150, 5, new Seed(1, 1, SeedTypes.PoppySeeds),  Season.Summer),
    SpangleSeeds("Spangle Seeds", "Plant in summer. Takes 8 days to produce a vibrant tropical flower. " +
            "Assorted colors.", 50, 75, 5, new Seed(1, 1,
            SeedTypes.SpangleSeeds),  Season.Summer),
    HopsStarter("Hops Starter", "Plant these in the summer. Takes 11 days to grow, but keeps producing " +
            "after that. Grows on a trellis.", 60, 90, 5, new Seed(1, 1,
            SeedTypes.HopsStarter),  Season.Summer),
    CornSeeds("Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and continues " +
            "to produce after first harvest.", 150, 225, 5, new Seed(1, 1,
            SeedTypes.CornSeeds),  Season.Summer),
    SunflowerSeeds("Sunflower Seeds", "Plant in summer or fall. Takes 8 days to produce a large " +
            "sunflower. Yields more seeds at harvest.", 200, 300, 5,
            new Seed(1, 1, SeedTypes.SunflowerSeeds),  Season.Summer),
    RedCabbageSeeds("Red Cabbage Seeds", "Plant these in the summer. Takes 9 days to mature.",
            100, 150, 5, new Seed(1, 1,
            SeedTypes.RedCabbageSeeds),  Season.Summer),

    // Fall products

    EggplantSeeds("Eggplant Seeds", "Plant these in the fall. Takes 5 days to mature, and continues" +
            " to produce after first harvest.", 25, 25, 5,
            new Seed(1, 1, SeedTypes.EggplantSeeds),  Season.Fall),
    PumpkinSeeds("Pumpkin Seeds", "Plant these in the fall. Takes 13 days to mature.", 125,
            125, 5, new Seed(1, 1, SeedTypes.PumpkinSeeds),  Season.Fall),
    BokChoySeeds("Bok Choy Seeds", "Plant these in the fall. Takes 4 days to mature.",
            50, 50, 5, new Seed(1, 1, SeedTypes.BokChoySeeds),  Season.Fall),
    YamSeeds("Yam Seeds", "Plant these in the fall. Takes 10 days to mature.", 75,
            75, 5, new Seed(1, 1, SeedTypes.YamSeeds),  Season.Fall),
    CranberrySeeds("Cranberry Seeds", "Plant these in the fall. Takes 7 days to mature, and " +
            "continues to produce after first harvest.", 300, 300, 5,
            new Seed(1, 1, SeedTypes.CranberrySeeds),  Season.Fall),
    FairySeeds("Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower. " +
            "Assorted Colors.", 250, 250, 5, new Seed(1, 1,
            SeedTypes.FairySeeds),  Season.Fall),
    AmaranthSeeds("Amaranth Seeds", "Plant these in the fall. Takes 7 days to grow. Harvest with the" +
            " scythe.", 87, 87, 5, new Seed(1, 1,
            SeedTypes.AmaranthSeeds),  Season.Fall),
    GrapeStarter("Grape Starter", "Plant these in the fall. Takes 10 days to grow, but keeps producing" +
            " after that. Grows on a trellis.", 75, 75, 5,
            new Seed(1, 1, SeedTypes.GrapeStarter),  Season.Fall),
    ArtichokeSeeds("Artichoke Seeds", "Plant these in the fall. Takes 8 days to mature.", 30,
            30, 5, new Seed(1, 1, SeedTypes.ArtichokeSeeds),  Season.Fall);

    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private final Item item;
    private final Integer priceOutOfSeason;
    private final  Season season;

    PierreGeneralStoreProduct(String name, String description, Integer price, Integer priceOutOfSeason,
                              Integer dailyLimit, Item item,  Season season) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.season = season;
        this.priceOutOfSeason = priceOutOfSeason;
    }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Integer getDailyLimit() {
        return this.dailyLimit;
    }
    public  Season getSeason() {
        return this.season;
    }
    public Integer getPriceOutOfSeason() {
        return this.priceOutOfSeason;
    }

    @Override
    public Item getItem() {
        if(Game.getInstance().getDate().getSeason() == season)
            item.setSellPrice(getPriceOutOfSeason());
        else
            item.setSellPrice(getPrice());
        item.setSellable(true);
        return item;
    }

    @Override
    public String getNameInStore() {
        return name;
    }
}
