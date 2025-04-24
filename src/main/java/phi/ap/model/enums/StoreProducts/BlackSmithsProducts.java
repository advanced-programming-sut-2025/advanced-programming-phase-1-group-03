package phi.ap.model.enums.StoreProducts;

import phi.ap.model.enums.BuildingTypes;
import phi.ap.model.enums.ForagingMineralTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;

public enum BlackSmithsProducts {
    CopperOre("Copper Ore", "A common ore that can be smelted into bars.", 75, Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.COPPER), StoreTypes.Blacksmith),
    IronOre("Iron Ore", "A fairly common ore that can be smelted into bars.", 75, Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.IRON), StoreTypes.Blacksmith),
    Coal("Coal", "A combustible rock that is useful for crafting and smelting.", 150, Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.COAL), StoreTypes.Blacksmith),
    GoldOre("Gold Ore", "A precious ore that can be smelted into bars.", 400, Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.GOLD), StoreTypes.Blacksmith);
    // TODO update Tools
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer dailyLimit;
    private final Item item;
    private final StoreTypes storeType;

    BlackSmithsProducts(String name, String description, Integer price, Integer dailyLimit, Item item, StoreTypes storeType) {
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
