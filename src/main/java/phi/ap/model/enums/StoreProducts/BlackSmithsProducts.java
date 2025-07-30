package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.Result;
import phi.ap.model.enums.ForagingMineralTypes;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;

public enum BlackSmithsProducts implements StoreItemProducer{
    Copper("Copper Ore", "A common ore that can be smelted into bars.", 75,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Copper), null, Tool.BasicToolLevels.copper),
    Iron("Iron Ore", "A fairly common ore that can be smelted into bars.", 75,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Iron),null,  Tool.BasicToolLevels.Iron),
    Coal("Coal", "A combustible rock that is useful for crafting and smelting.", 150,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Coal),null,  null),
    Gold("Gold Ore", "A precious ore that can be smelted into bars.", 400,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Gold),null,  Tool.BasicToolLevels.golden),
    CopperTool("Copper Tool", "", 2000, 1,null,  new ItemStack(ForagingMineralTypes.CopperBar.getItem(), 5), Tool.BasicToolLevels.copper),
    SteelTool("Steel Tool", "", 5000, 1,null,  new ItemStack(ForagingMineralTypes.IronBar.getItem(), 5), Tool.BasicToolLevels.Iron),
    GoldTool("Gold Tool", "", 10000, 1,null,  new ItemStack(ForagingMineralTypes.GoldBar.getItem(), 5), Tool.BasicToolLevels.golden),
    IridiumTool("Iridium Tool", "", 25000, 1,null,  new ItemStack(ForagingMineralTypes.IridiumBar.getItem(), 5), Tool.BasicToolLevels.iridium),
    CopperTrashcan("Copper Trashcan", "", 1000, 1,null,  new ItemStack(ForagingMineralTypes.CopperBar.getItem(), 5), Tool.BasicToolLevels.copper),
    SteelTrashcan("Steel Trashcan", "", 2500, 1,null,  new ItemStack(ForagingMineralTypes.IronBar.getItem(), 5), Tool.BasicToolLevels.Iron),
    GoldTrashcan("Gold Trashcan", "", 5000, 1,null,  new ItemStack(ForagingMineralTypes.GoldBar.getItem(), 5), Tool.BasicToolLevels.golden),
    IridiumTrashcan("Iridium Trashcan", "", 12500,1,null,  new ItemStack(ForagingMineralTypes.IridiumBar.getItem(), 5), Tool.BasicToolLevels.iridium);
    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Tool.BasicToolLevels level;
    private Item item;
    private ItemStack ingredient;
    BlackSmithsProducts(String name, String description, Integer price, Integer dailyLimit, Item item ,ItemStack ingredient, Tool.BasicToolLevels level) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.level = level;
        this.ingredient = ingredient;
        this.item = item;
    }

    public static BlackSmithsProducts getNextLevelTrashcan(Tool.BasicToolLevels level){
        return switch (level) {
            case normal -> BlackSmithsProducts.CopperTrashcan;
            case copper -> BlackSmithsProducts.SteelTrashcan;
            case Iron -> BlackSmithsProducts.GoldTrashcan;
            case golden -> BlackSmithsProducts.IridiumTrashcan;
            case null, default -> null;
        };
    }
    public static BlackSmithsProducts getNextLevelTool(Tool.BasicToolLevels level){
        return switch (level) {
            case normal -> BlackSmithsProducts.CopperTool;
            case copper -> BlackSmithsProducts.SteelTool;
            case Iron -> BlackSmithsProducts.GoldTool;
            case golden -> BlackSmithsProducts.IridiumTool;
            case null, default -> null;
        };
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

    public ItemStack getIngredient() {
        return ingredient;
    }
}
