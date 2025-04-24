package phi.ap.model.enums.StoreProducts;

import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.SeedTypes;
import phi.ap.model.enums.SoilTypes;
import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.items.Item;
import phi.ap.model.items.producers.Seed;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.products.Soil;

public enum JojaMarketProducts {
    // Season doesn't matter
    JOJACOLA("Joja Cola", "The flagship product of Joja corporation.", 75, 1, new Food(1, 1, FoodTypes.JojaCola), null),
    ANCIENTSEED("Ancient Seed", "Could these still grow?", 500, 1, new Seed(1, 1, SeedTypes.AncientSeeds), null),
    GRASS_STARTER("Grass Starter", "Place this on your farm to start a new patch of grass.", 125, Integer.MAX_VALUE, new Soil(1, 1, SoilTypes.GrassStarter), null),
    SUGAR("Sugar", "Adds sweetness to pastries and candies. Too much can be unhealthy.", 125, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Sugar), null),
    WHEAT_FLOUR("Wheat Flour", "A common cooking ingredient made from crushed wheat seeds.", 125, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.WheatFlour), null),
    RICE("Rice", "A basic grain often served under vegetables.", 250, 1, new Food(1, 1, FoodTypes.Rice), null),
    // Spring
    PARSNIP_SEEDS("Parsnip Seeds", "Plant these in the spring. Takes 4 days to mature.", 25, 5, new Seed(1, 1, SeedTypes.ParsnipSeeds), Seasons.Spring),
    BEAN_STARTER("Bean Starter", "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.", 75, 5, new Seed(1, 1, SeedTypes.BeanStarter), Seasons.Spring),
    CAULIFLOWER_SEEDS("Cauliflower Seeds", "Plant these in the spring. Takes 12 days to produce a large cauliflower.", 100, 5, new Seed(1, 1, SeedTypes.CauliflowerSeeds), Seasons.Spring),
    POTATO_SEEDS("Potato Seeds", "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.", 62, 5, new Seed(1, 1, SeedTypes.PotatoSeeds), Seasons.Spring),
    STRAWBERRY_SEEDS("Strawberry Seeds", "Plant these in spring. Takes 8 days to mature, and keeps producing strawberries after that.", 100, 5, new Seed(1, 1, SeedTypes.StrawberrySeeds), Seasons.Spring),
    TULIP_BULB("Tulip Bulb", "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.", 25, 5, new Seed(1, 1, SeedTypes.TulipBulb), Seasons.Spring),
    KALE_SEEDS("Kale Seeds", "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.", 87, 5, new Seed(1, 1, SeedTypes.KaleSeeds), Seasons.Spring),
    COFFEE_BEANS("Coffee Beans", "Plant in summer or spring. Takes 10 days to grow, then produces coffee beans every other day.", 200, 5, new Seed(1, 1, SeedTypes.CoffeeBean), Seasons.Spring),
    CARROT_SEEDS("Carrot Seeds", "Plant in the spring. Takes 3 days to grow.", 5, 10, new Seed(1, 1, SeedTypes.CarrotSeeds), Seasons.Spring),
    RHUBARB_SEEDS("Rhubarb Seeds", "Plant these in the spring. Takes 13 days to mature.", 100, 5, new Seed(1, 1, SeedTypes.RhubarbSeeds), Seasons.Spring),
    JAZZ_SEEDS("Jazz Seeds", "Plant in spring. Takes 7 days to produce a blue puffball flower.", 37, 5, new Seed(1, 1, SeedTypes.JazzSeeds), Seasons.Spring),
    // Summer
    CORN_SEEDS("Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.", 187, 5, new Seed(1, 1, SeedTypes.CornSeeds), Seasons.Summer),
    EGGPLANT_SEEDS("Eggplant Seeds", "Plant these in the fall. Takes 5 days to mature, and continues to produce after first harvest.", 25, 5, new Seed(1, 1, SeedTypes.EggplantSeeds), Seasons.Summer),
    PUMPKIN_SEEDS("Pumpkin Seeds", "Plant these in the fall. Takes 13 days to mature.", 125, 5, new Seed(1, 1, SeedTypes.PumpkinSeeds), Seasons.Summer),
    BROCCOLI_SEEDS("Broccoli Seeds", "Plant in the fall. Takes 8 days to mature, and continues to produce after first harvest.", 15, 5, new Seed(1, 1, SeedTypes.BroccoliSeeds), Seasons.Summer),
    AMARANTH_SEEDS("Amaranth Seeds", "Plant these in the fall. Takes 7 days to grow. Harvest with the scythe.", 87, 5, new Seed(1, 1, SeedTypes.AmaranthSeeds), Seasons.Summer),
    GRAPE_STARTER("Grape Starter", "Plant these in the fall. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.", 75, 5, new Seed(1, 1, SeedTypes.GrapeStarter), Seasons.Summer),
    BEET_SEEDS("Beet Seeds", "Plant these in the fall. Takes 6 days to mature.", 20, 5, new Seed(1, 1, SeedTypes.BeetSeeds), Seasons.Summer),
    YAM_SEEDS("Yam Seeds", "Plant these in the fall. Takes 10 days to mature.", 75, 5, new Seed(1, 1, SeedTypes.CornSeeds), Seasons.Summer),
    BOK_CHOY_SEEDS("Bok Choy Seeds", "Plant these in the fall. Takes 4 days to mature.", 20, 5, new Seed(1, 1, SeedTypes.YamSeeds), Seasons.Summer),
    CRANBERRY_SEEDS("Cranberry Seeds", "Plant these in the fall. Takes 7 days to mature, and continues to produce after first harvest.", 62, 5, new Seed(1, 1, SeedTypes.CranberrySeeds), Seasons.Summer),
    SUNFLOWER_SEEDS("Sunflower Seeds", "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.", 300, 5, new Seed(1, 1, SeedTypes.SunflowerSeeds), Seasons.Summer),
    FAIRY_SEEDS("Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted Colors.", 125, 5, new Seed(1, 1, SeedTypes.FairySeeds), Seasons.Summer),
    RARE_SEED("Rare Seed", "Sow in fall. Takes all season to grow.", 250, 5, new Seed(1, 1, SeedTypes.RareSeed), Seasons.Summer),
    WHEAT_SEEDS("Wheat Seeds", "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe.", 12, 5, new Seed(1, 1, SeedTypes.WheatSeeds), Seasons.Summer),
    // Fall
    CornSeeds("Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.", 187, 5, new Seed(1, 1, SeedTypes.CornSeeds), Seasons.Fall),
    EggplantSeeds("Eggplant Seeds", "Plant these in the fall. Takes 5 days to mature, and continues to produce after first harvest.", 25, 5, new Seed(1, 1, SeedTypes.EggplantSeeds), Seasons.Fall),
    PumpkinSeeds("Pumpkin Seeds", "Plant these in the fall. Takes 13 days to mature.", 125, 5, new Seed(1, 1, SeedTypes.PumpkinSeeds), Seasons.Fall),
    BroccoliSeeds("Broccoli Seeds", "Plant in the fall. Takes 8 days to mature, and continues to produce after first harvest.", 15, 5, new Seed(1, 1, SeedTypes.BroccoliSeeds), Seasons.Fall),
    AmaranthSeeds("Amaranth Seeds", "Plant these in the fall. Takes 7 days to grow. Harvest with the scythe.", 87, 5, new Seed(1, 1, SeedTypes.AmaranthSeeds), Seasons.Fall),
    GrapeStarter("Grape Starter", "Plant these in the fall. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.", 75, 5, new Seed(1, 1, SeedTypes.GrapeStarter), Seasons.Fall),
    BeetSeeds("Beet Seeds", "Plant these in the fall. Takes 6 days to mature.", 20, 5, new Seed(1, 1, SeedTypes.BeetSeeds), Seasons.Fall),
    YamSeeds("Yam Seeds", "Plant these in the fall. Takes 10 days to mature.", 75, 5, new Seed(1, 1, SeedTypes.YamSeeds), Seasons.Fall),
    BokChoySeeds("Bok Choy Seeds", "Plant these in the fall. Takes 4 days to mature.", 62, 5, new Seed(1, 1, SeedTypes.BokChoySeeds), Seasons.Fall),
    CranberrySeeds("Cranberry Seeds", "Plant these in the fall. Takes 7 days to mature, and continues to produce after first harvest.", 300, 5, new Seed(1, 1, SeedTypes.CranberrySeeds), Seasons.Fall),
    SunflowerSeeds("Sunflower Seeds", "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.", 125, 5, new Seed(1, 1, SeedTypes.SunflowerSeeds), Seasons.Fall),
    FairySeeds("Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted Colors.", 250, 5, new Seed(1, 1, SeedTypes.FairySeeds), Seasons.Fall),
    RareSeed("Rare Seed", "Sow in fall. Takes all season to grow.", 1000, 1, new Seed(1, 1, SeedTypes.RareSeed), Seasons.Fall),
    WheatSeeds("Wheat Seeds", "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe.", 12, 5, new Seed(1, 1, SeedTypes.WheatSeeds), Seasons.Fall),
    // Winter
    PowdermelonSeeds("Powdermelon Seeds", "This special melon grows in the winter. Takes 7 days to grow.", 20, 10, new Seed(1, 1, SeedTypes.PowdermelonSeeds), Seasons.Winter);
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer dailyLimit;
    private final Item item;
    private final Seasons season;

    JojaMarketProducts(String name, String description, Integer price, Integer dailyLimit, Item item, Seasons season) {
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
    public Seasons getSeason() {
        return this.season;
    }
}
