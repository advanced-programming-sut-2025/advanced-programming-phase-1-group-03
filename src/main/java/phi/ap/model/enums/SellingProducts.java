package phi.ap.model.enums;

import phi.ap.model.items.Item;
import phi.ap.model.items.producers.Seed;
import phi.ap.model.items.tools.MilkPail;
import phi.ap.model.items.tools.Shear;

public enum SellingProducts {
//    CopperOre("Copper Ore", "A common ore that can be smelted into bars.", 75, Integer.MAX_VALUE),
//    IronOre("Iron Ore", "A fairly common ore that can be smelted into bars.", 150, Integer.MAX_VALUE),
//    Coal("Coal", "A combustible rock that is useful for crafting and smelting.", 150, Integer.MAX_VALUE),
//    GoldOre("Cold Ore", "A precious ore that can be smelted into bars.", 400, Integer.MAX_VALUE);
//    CornSeed("Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.", 	187,	5, new Seed(SeedTypes.CornSeeds));
    // Marnie's Ranch
    MilkPay("Milk Pail", "Gather milk from your animals.",	1000,	1, new MilkPail(), StoreTypes.MarnieRanch),
    Shear("Shears", "Use this to collect wool from sheep", 	1000,	1, new Shear(), StoreTypes.MarnieRanch);
    // TODO Hay and Lives Tock


    private final String name;
    private final String description;
    private final Integer price;
    private final Integer dailyLimit;
    private final Item item;
    private final StoreTypes storeType;

    SellingProducts(String name, String description, Integer price, Integer dailyLimit, Item item, StoreTypes storeType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.storeType = storeType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }
}
