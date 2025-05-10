package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.Item;
import phi.ap.model.items.tools.FishingPole;

public enum FishShopProducts {
    FishSmoker("Fish Smoker (Recipe)", "A recipe to make Fish Smoker",
            10000, 1, 0, new FishingPole(1, 1, LevelName.initial)),
    TroutSoup("Trout Soup", "Pretty salty.", 250, 1,
            0, new FishingPole(1, 1, LevelName.bamboo)),
    BambooPole("Bamboo Pole", "Use in the water to catch fish.", 500,
            1, 0, new FishingPole(1, 1, LevelName.bamboo)),
    TrainingRod("Training Rod", "It's a lot easier to use than other rods, but can only catch " +
            "basic fish.", 25, 1, 0, new FishingPole(1, 1, LevelName.training)),
    FiberglassRod("Fiberglass Rod", "Use in the water to catch fish.", 1800,
            1, 2, new FishingPole(1, 1, LevelName.fiberGlass)),
    IridiumRod("Iridium Rod", "Use in the water to catch fish.", 7500,
            1, 4, new FishingPole(1, 1, LevelName.iridium));
    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;
    private final Integer fishingSkill;
    private final Item item;
    FishShopProducts(String name, String description, Integer price, Integer dailyLimit, Integer fishingSkill,
                     Item item) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.fishingSkill = fishingSkill;
    }


    public static Result<String> purchase(String productName, String amountString) {
        int amount = Integer.parseInt(amountString);
        FishShopProducts fishShopProducts;
        try {
            fishShopProducts  = FishShopProducts.valueOf(productName);
        }
        catch (IllegalArgumentException e) {
            return new Result<>(false, "There is no product with this name.");
        }
        if(amount > fishShopProducts.availableAmount) {
            return new Result<>(false, "There is not enough amount of this product.");
        }
        else if(amount > fishShopProducts.dailyLimit) {
            return new Result<>(false, "You can't purchase this amount of product on this day.");
        }
        else if(amount * fishShopProducts.price > Game.getInstance().getCurrentPlayer().getGold()) {
            return new Result<>(false, "You don't have enough money.");
        }
        fishShopProducts.availableAmount -= amount;
        fishShopProducts.dailyLimit -= amount;
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() - amount *
                fishShopProducts.price);
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(fishShopProducts.item, amount);
        return new Result<>(true, "Item purchased successfully");
    }

    public static Result<String> showAllProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(FishShopProducts fishShopProducts : FishShopProducts.values()) {
            stringBuilder.append("Name : " + "\"" + fishShopProducts.getName() + "\"" + "     " + "Price: "  +
                    fishShopProducts.getPrice() + "g" + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }

    public static Result<String> showAvailableProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(FishShopProducts fishShopProducts : FishShopProducts.values()) {
            if(fishShopProducts.availableAmount > 0 && fishShopProducts.dailyLimit > 0)
                stringBuilder.append("Name : " + "\"" + fishShopProducts.getName() + "\"" + "     " + "Price: "  +
                        fishShopProducts.getPrice() + "g" + "\n");
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
