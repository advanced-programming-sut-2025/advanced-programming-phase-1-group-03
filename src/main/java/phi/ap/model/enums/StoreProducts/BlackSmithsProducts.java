package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.ForagingMineralTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;

public enum BlackSmithsProducts {
    CopperOre("Copper Ore", "A common ore that can be smelted into bars.", 75,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Copper), StoreTypes.Blacksmith),
    IronOre("Iron Ore", "A fairly common ore that can be smelted into bars.", 75,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Iron), StoreTypes.Blacksmith),
    Coal("Coal", "A combustible rock that is useful for crafting and smelting.", 150,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Coal), StoreTypes.Blacksmith),
    GoldOre("Gold Ore", "A precious ore that can be smelted into bars.", 400,
            Integer.MAX_VALUE, new Mineral(1, 1, ForagingMineralTypes.Gold), StoreTypes.Blacksmith);
    // TODO update Tools
    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;
    private final Item item;
    private final StoreTypes storeType;
    BlackSmithsProducts(String name, String description, Integer price, Integer dailyLimit, Item item,
                        StoreTypes storeType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.storeType = storeType;
    }
    public static Result<String> purchase(String productName, String amountString) {
        int amount = Integer.parseInt(amountString);
        BlackSmithsProducts blackSmithsProducts;
        try {
            blackSmithsProducts = BlackSmithsProducts.valueOf(productName);
        }
        catch (IllegalArgumentException e) {
            return new Result<>(false, "There is no product with this name.");
        }
        if(amount > blackSmithsProducts.availableAmount) {
            return new Result<>(false, "There is not enough amount of this product.");
        }
        else if(amount > blackSmithsProducts.dailyLimit) {
            return new Result<>(false, "You can't purchase this amount of product on this day.");
        }
        else if(amount * blackSmithsProducts.price > Game.getInstance().getCurrentPlayer().getGold()) {
            return new Result<>(false, "You don't have enough money.");
        }
        blackSmithsProducts.availableAmount -= amount;
        blackSmithsProducts.dailyLimit -= amount;
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() -
                amount * blackSmithsProducts.price);
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(blackSmithsProducts.item, amount);
        return new Result<>(true, "Item purchased successfully");
    }
    public static Result<String> showAllProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(BlackSmithsProducts blackSmithsProducts : BlackSmithsProducts.values()) {
            stringBuilder.append("Name : " + "\"" + blackSmithsProducts.getName() + "\"" + "     " + "Price: "  +
                    blackSmithsProducts.getPrice() + "g" + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }

    public static Result<String> showAvailableProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(BlackSmithsProducts blackSmithsProducts : BlackSmithsProducts.values()) {
            if(blackSmithsProducts.availableAmount > 0 && blackSmithsProducts.dailyLimit > 0)
                stringBuilder.append("Name : " + "\"" + blackSmithsProducts.getName() + "\"" + "     " + "Price: "  +
                        blackSmithsProducts.getPrice() + "g" + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
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
