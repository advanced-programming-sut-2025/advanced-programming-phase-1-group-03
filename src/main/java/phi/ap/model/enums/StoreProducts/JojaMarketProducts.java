package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.SeedTypes;
import phi.ap.model.enums.SoilTypes;
import phi.ap.model.enums.Time. Season;
import phi.ap.model.items.Item;
import phi.ap.model.items.Seed;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.products.Soil;

public enum JojaMarketProducts implements StoreItemProducer{
    // Season doesn't matter
    JOJACOLA("Joja Cola", "The flagship product of Joja corporation.", 75, 1,
            new Food(1, 1, FoodTypes.JojaCola), null),
    ANCIENTSEED("Ancient Seed", "Could these still grow?", 500, 1,
            new Seed(1, 1, SeedTypes.AncientSeeds), null),
    GRASS_STARTER("Grass Starter", "Place this on your farm to start a new patch of grass.", 125,
            Integer.MAX_VALUE, new Soil(1, 1, SoilTypes.GrassStarter), null),
    SUGAR("Sugar", "Adds sweetness to pastries and candies. Too much can be unhealthy.", 125,
            Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Sugar), null),
    WHEAT_FLOUR("Wheat Flour", "A common cooking ingredient made from crushed wheat seeds.", 125,
            Integer.MAX_VALUE, new Food(1, 1, FoodTypes.WheatFlour), null),
    RICE("Rice", "A basic grain often served under vegetables.", 250, 1,
            new Food(1, 1, FoodTypes.Rice), null),
    // Spring
    PARSNIP_SEEDS("Parsnip Seeds", "Plant these in the spring. Takes 4 days to mature.", 25, 5,
            new Seed(1, 1, SeedTypes.ParsnipSeeds),  Season.Spring),
    BEAN_STARTER("Bean Starter", "Plant these in the spring. Takes 10 days to mature, but keeps " +
            "producing after that. Grows on a trellis.", 75, 5, new Seed(1, 1,
            SeedTypes.BeanStarter),  Season.Spring),
    CAULIFLOWER_SEEDS("Cauliflower Seeds", "Plant these in the spring. Takes 12 days to produce a large " +
            "cauliflower.", 100, 5, new Seed(1, 1, SeedTypes.CauliflowerSeeds),  Season.Spring),
    POTATO_SEEDS("Potato Seeds", "Plant these in the spring. Takes 6 days to mature, and has a chance of " +
            "yielding multiple potatoes at harvest.", 62, 5, new Seed(1, 1,
            SeedTypes.PotatoSeeds),  Season.Spring),
    STRAWBERRY_SEEDS("Strawberry Seeds", "Plant these in spring. Takes 8 days to mature, and " +
            "keeps producing strawberries after that.", 100, 5, new Seed(1, 1,
            SeedTypes.StrawberrySeeds),  Season.Spring),
    TULIP_BULB("Tulip Bulb", "Plant in spring. Takes 6 days to produce a colorful flower. " +
            "Assorted colors.", 25, 5, new Seed(1, 1, SeedTypes.TulipBulb),  Season.Spring),
    KALE_SEEDS("Kale Seeds", "Plant these in the spring. Takes 6 days to mature. Harvest with " +
            "the scythe.", 87, 5, new Seed(1, 1, SeedTypes.KaleSeeds),  Season.Spring),
    COFFEE_BEANS("Coffee Beans", "Plant in summer or spring. Takes 10 days to grow, then produces " +
            "coffee beans every other day.", 200, 5, new Seed(1, 1, SeedTypes.CoffeeBean),
             Season.Spring),
    CARROT_SEEDS("Carrot Seeds", "Plant in the spring. Takes 3 days to grow.", 5, 10,
            new Seed(1, 1, SeedTypes.CarrotSeeds),  Season.Spring),
    RHUBARB_SEEDS("Rhubarb Seeds", "Plant these in the spring. Takes 13 days to mature.", 100,
            5, new Seed(1, 1, SeedTypes.RhubarbSeeds),  Season.Spring),
    JAZZ_SEEDS("Jazz Seeds", "Plant in spring. Takes 7 days to produce a blue puffball flower.",
            37, 5, new Seed(1, 1, SeedTypes.JazzSeeds),  Season.Spring),
    // Summer
    CORN_SEEDS("Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and continues" +
            " to produce after first harvest.", 187, 5, new Seed(1, 1, SeedTypes.CornSeeds),
             Season.Summer),
    EGGPLANT_SEEDS("Eggplant Seeds", "Plant these in the fall. Takes 5 days to mature, and continues" +
            " to produce after first harvest.", 25, 5, new Seed(1, 1,
            SeedTypes.EggplantSeeds),  Season.Summer),
    PUMPKIN_SEEDS("Pumpkin Seeds", "Plant these in the fall. Takes 13 days to mature.",
            125, 5, new Seed(1, 1, SeedTypes.PumpkinSeeds),  Season.Summer),
    BROCCOLI_SEEDS("Broccoli Seeds", "Plant in the fall. Takes 8 days to mature, and " +
            "continues to produce after first harvest.", 15, 5, new Seed(1, 1,
            SeedTypes.BroccoliSeeds),  Season.Summer),
    AMARANTH_SEEDS("Amaranth Seeds", "Plant these in the fall. Takes 7 days to grow. " +
            "Harvest with the scythe.", 87, 5, new Seed(1, 1,
            SeedTypes.AmaranthSeeds),  Season.Summer),
    GRAPE_STARTER("Grape Starter", "Plant these in the fall. Takes 10 days to grow, " +
            "but keeps producing after that. Grows on a trellis.", 75, 5, new Seed(1, 1,
            SeedTypes.GrapeStarter),  Season.Summer),
    BEET_SEEDS("Beet Seeds", "Plant these in the fall. Takes 6 days to mature.", 20, 5,
            new Seed(1, 1, SeedTypes.BeetSeeds),  Season.Summer),
    YAM_SEEDS("Yam Seeds", "Plant these in the fall. Takes 10 days to mature.", 75, 5,
            new Seed(1, 1, SeedTypes.CornSeeds),  Season.Summer),
    BOK_CHOY_SEEDS("Bok Choy Seeds", "Plant these in the fall. Takes 4 days to mature.", 20,
            5, new Seed(1, 1, SeedTypes.YamSeeds),  Season.Summer),
    CRANBERRY_SEEDS("Cranberry Seeds", "Plant these in the fall. Takes 7 days to mature, and continues " +
            "to produce after first harvest.", 62, 5, new Seed(1, 1,
            SeedTypes.CranberrySeeds),  Season.Summer),
    SUNFLOWER_SEEDS("Sunflower Seeds", "Plant in summer or fall. Takes 8 days to produce a " +
            "large sunflower. Yields more seeds at harvest.", 300, 5, new Seed(1, 1,
            SeedTypes.SunflowerSeeds),  Season.Summer),
    FAIRY_SEEDS("Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted " +
            "Colors.", 125, 5, new Seed(1, 1, SeedTypes.FairySeeds),  Season.Summer),
    RARE_SEED("Rare Seed", "Sow in fall. Takes all season to grow.", 250, 5,
            new Seed(1, 1, SeedTypes.RareSeed),  Season.Summer),
    WHEAT_SEEDS("Wheat Seeds", "Plant these in the summer or fall. Takes 4 days to mature. " +
            "Harvest with the scythe.", 12, 5, new Seed(1, 1,
            SeedTypes.WheatSeeds),  Season.Summer),
    // Fall
    CornSeeds("Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and " +
            "continues to produce after first harvest.", 187, 5, new Seed(1, 1,
            SeedTypes.CornSeeds),  Season.Fall),
    EggplantSeeds("Eggplant Seeds", "Plant these in the fall. Takes 5 days to mature, and " +
            "continues to produce after first harvest.", 25, 5, new Seed(1, 1,
            SeedTypes.EggplantSeeds),  Season.Fall),
    PumpkinSeeds("Pumpkin Seeds", "Plant these in the fall. Takes 13 days to mature.", 125,
            5, new Seed(1, 1, SeedTypes.PumpkinSeeds),  Season.Fall),
    BroccoliSeeds("Broccoli Seeds", "Plant in the fall. Takes 8 days to mature, and continues to " +
            "produce after first harvest.", 15, 5, new Seed(1, 1,
            SeedTypes.BroccoliSeeds),  Season.Fall),
    AmaranthSeeds("Amaranth Seeds", "Plant these in the fall. Takes 7 days to grow. Harvest " +
            "with the scythe.", 87, 5, new Seed(1, 1, SeedTypes.AmaranthSeeds),  Season.Fall),
    GrapeStarter("Grape Starter", "Plant these in the fall. Takes 10 days to grow, " +
            "but keeps producing after that. Grows on a trellis.", 75, 5,
            new Seed(1, 1, SeedTypes.GrapeStarter),  Season.Fall),
    BeetSeeds("Beet Seeds", "Plant these in the fall. Takes 6 days to mature.",
            20, 5, new Seed(1, 1, SeedTypes.BeetSeeds),  Season.Fall),
    YamSeeds("Yam Seeds", "Plant these in the fall. Takes 10 days to mature.",
            75, 5, new Seed(1, 1, SeedTypes.YamSeeds),  Season.Fall),
    BokChoySeeds("Bok Choy Seeds", "Plant these in the fall. Takes 4 days to mature.",
            62, 5, new Seed(1, 1, SeedTypes.BokChoySeeds),  Season.Fall),
    CranberrySeeds("Cranberry Seeds", "Plant these in the fall. Takes 7 days to mature, and " +
            "continues to produce after first harvest.", 300, 5, new Seed(1, 1,
            SeedTypes.CranberrySeeds),  Season.Fall),
    SunflowerSeeds("Sunflower Seeds", "Plant in summer or fall. Takes 8 days to produce a " +
            "large sunflower. Yields more seeds at harvest.", 125, 5, new Seed(1, 1,
            SeedTypes.SunflowerSeeds),  Season.Fall),
    FairySeeds("Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower. " +
            "Assorted Colors.", 250, 5, new Seed(1, 1, SeedTypes.FairySeeds),  Season.Fall),
    RareSeed("Rare Seed", "Sow in fall. Takes all season to grow.", 1000, 1,
            new Seed(1, 1, SeedTypes.RareSeed),  Season.Fall),
    WheatSeeds("Wheat Seeds", "Plant these in the summer or fall. Takes 4 days to mature. " +
            "Harvest with the scythe.", 12, 5, new Seed(1, 1,
            SeedTypes.WheatSeeds),  Season.Fall),
    // Winter
    PowdermelonSeeds("Powdermelon Seeds", "This special melon grows in the winter. Takes 7 days to grow."
            , 20, 10, new Seed(1, 1, SeedTypes.PowdermelonSeeds),  Season.Winter);
    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;
    private final Item item;
    private final  Season season;

    JojaMarketProducts(String name, String description, Integer price, Integer dailyLimit, Item item,  Season season) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.season = season;
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

    @Override
    public Item getItem() {
        item.setSellable(true);
        item.setSellPrice(getPrice());
        return item;
    }

    @Override
    public String getNameInStore() {
        return name;
    }
}
