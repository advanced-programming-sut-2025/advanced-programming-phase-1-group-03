package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.BuildingTypes;
import phi.ap.model.enums.StoneTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Barn;
import phi.ap.model.items.buildings.Coop;
import phi.ap.model.items.buildings.ShippingBin;
import phi.ap.model.items.buildings.Well;
import phi.ap.model.items.products.Stone;
import phi.ap.model.items.products.Wood;

public enum CarpenterShopProducts {
    Wood("Wood", "A sturdy, yet flexible plant material with a wide variety of uses.", 10, null, null, null, null, Integer.MAX_VALUE, new Wood(1, 1)),
    Stone("Wood", "A sturdy, yet flexible plant material with a wide variety of uses.", 20, null, null, null, null, Integer.MAX_VALUE, new Stone(1, 1, StoneTypes.RegularStone)),
    Barn("Barn", "Houses 4 barn-dwelling animals.", 6000, 350, 150, 7, 4, 1, new Barn(7, 4, BuildingTypes.Barn)),
    BigBarn("Big Barn", "Houses 8 barn-dwelling animals. Unlocks goats.", 12000, 450, 200, 7, 4, 1, new Barn(7, 4, BuildingTypes.BigBarn)),
    DeluxeBarn("Deluxe Barn", "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.", 25000, 550, 300, 7, 4, 1, new Barn(7, 4, BuildingTypes.DeluxeBarn)),
    Coop("Coop", "Houses 4 coop-dwelling animals.", 4000, 300, 100, 6, 3, 1, new Coop(6, 3, BuildingTypes.Coop)),
    BigCoop("Big Coop", "Houses 8 coop-dwelling animals. Unlocks ducks.", 10000, 400, 150, 6, 3, 1, new Coop(6, 3, BuildingTypes.BigCoop)),
    DeluxeCoop("Deluxe Coop", "Houses 12 coop-dwelling animals. Unlocks rabbits.", 20000, 500, 200, 6, 3, 1, new Coop(6, 3, BuildingTypes.DeluxeCoop)),
    Well("Well", "Provides a place for you to refill your watering can.", 1000, 0, 75, 3, 3, 1, new Well(3, 3)),
    ShippingBin("Shipping Bin", "Items placed in it will be included in the nightly shipment.", 250, 150, 0, 1, 1, Integer.MAX_VALUE, new ShippingBin(1, 1));
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer neededWood;
    private final Integer neededStone;
    private final Integer height;
    private final Integer width;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;
    private final Item item;

    CarpenterShopProducts(String name, String description, Integer price, Integer neededWood, Integer neededStone, Integer height, Integer width, Integer dailyLimit, Item item) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.neededWood = neededWood;
        this.neededStone = neededStone;
        this.height = height;
        this.width = width;
    }
    public static Result<String> purchase(String productName, String amountString) {
        int amount = Integer.parseInt(amountString);
        CarpenterShopProducts carpenterShopProducts;
        try {
            carpenterShopProducts = CarpenterShopProducts.valueOf(productName);
        }
        catch (IllegalArgumentException e) {
            return new Result<>(false, "There is no product with this name.");
        }
        if(amount > carpenterShopProducts.availableAmount) {
            return new Result<>(false, "There is not enough amount of this product.");
        }
        else if(amount > carpenterShopProducts.dailyLimit) {
            return new Result<>(false, "You can't purchase this amount of product on this day.");
        }
        else if(amount * carpenterShopProducts.price > Game.getInstance().getCurrentPlayer().getGold()) {
            return new Result<>(false, "You don't have enough money.");
        }
        carpenterShopProducts.availableAmount -= amount;
        carpenterShopProducts.dailyLimit -= amount;
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() - amount * carpenterShopProducts.price);
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(carpenterShopProducts.item, amount);
        return new Result<>(true, "Item purchased successfully");
    }
    public static Result<String> showAllProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(CarpenterShopProducts carpenterShopProducts : CarpenterShopProducts.values()) {
            stringBuilder.append("Name : " + "\"" + carpenterShopProducts.getName() + "\"" + "     " + "Price: "  + carpenterShopProducts.getPrice() + "g" + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public static Result<String> showAvailableProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(CarpenterShopProducts carpenterShopProducts : CarpenterShopProducts.values()) {
            if(carpenterShopProducts.dailyLimit > 0 && carpenterShopProducts.availableAmount > 0)
                stringBuilder.append("Name : " + "\"" + carpenterShopProducts.getName() + "\"" + "     " + "Price: "  + carpenterShopProducts.getPrice() + "g" + "\n");
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
