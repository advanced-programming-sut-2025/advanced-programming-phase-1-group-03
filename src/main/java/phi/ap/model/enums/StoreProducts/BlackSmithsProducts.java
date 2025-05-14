package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.ForagingMineralTypes;
import phi.ap.model.enums.LevelName;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;

public enum BlackSmithsProducts implements StoreItemProducer{
    CopperOre("Copper Ore", "A common ore that can be smelted into bars.", 75,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Copper), LevelName.copper),
    IronOre("Iron Ore", "A fairly common ore that can be smelted into bars.", 75,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Iron), LevelName.iron),
    Coal("Coal", "A combustible rock that is useful for crafting and smelting.", 150,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Coal), null),
    GoldOre("Gold Ore", "A precious ore that can be smelted into bars.", 400,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Gold), LevelName.golden),
    IridiumOre("Iridium Ore", "A precious ore that can be smelted into bars.", 600,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Gold), LevelName.iridium),
    CopperTool("Copper Tool", "", 2000, 1, BlackSmithsProducts.CopperOre.getItem(), LevelName.copper),
    SteelTool("Steel Tool", "", 5000, 1, BlackSmithsProducts.IronOre.getItem(), LevelName.iron),
    GoldTool("Gold Tool", "", 10000, 1, BlackSmithsProducts.GoldOre.getItem(), LevelName.golden),
    IridiumTool("Iridium Tool", "", 2000, 1, BlackSmithsProducts.IridiumOre.getItem(), LevelName.iridium),
    CopperTrashcan("Copper Trashcan", "", 2000, 1, BlackSmithsProducts.CopperOre.getItem(), LevelName.copper),
    SteelTrashcan("Steel Trashcan", "", 2000, 1, BlackSmithsProducts.IronOre.getItem(), LevelName.iron),
    GoldTrashcan("Gold Trashcan", "", 2000, 1, BlackSmithsProducts.GoldOre.getItem(),LevelName.golden),
    IridiumTrashcan("Iridium Trashcan", "", 2000,1, null, LevelName.iridium);
    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private LevelName level;
    private Item item;
    BlackSmithsProducts(String name, String description, Integer price, Integer dailyLimit, Item item, LevelName level) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.level = level;
        this.item = item;
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

    @Override
    public String getNameInStore() {
        return name;
    }

    @Override
    public Item getItem() {
        return item;
    }
}
