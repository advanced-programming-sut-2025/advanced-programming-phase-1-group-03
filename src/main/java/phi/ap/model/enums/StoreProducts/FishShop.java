package phi.ap.model.enums.StoreProducts;

import phi.ap.model.enums.LevelName;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.tools.FishingPole;

public enum FishShop {
    FishSmoker("Fish Smoker (Recipe)", "A recipe to make Fish Smoker", 10000, 1, 0, new FishingPole(1, 1, LevelName.initial)),
    TroutSoup("Trout Soup", "Pretty salty.", 250, 1, 0, new FishingPole(1, 1, LevelName.bamboo)),
    BambooPole("Bamboo Pole", "Use in the water to catch fish.", 500, 1, 0, new FishingPole(1, 1, LevelName.bamboo)),
    TrainingRod("Training Rod", "It's a lot easier to use than other rods, but can only catch basic fish.", 25, 1, 0, new FishingPole(1, 1, LevelName.training)),
    FiberglassRod("Fiberglass Rod", "Use in the water to catch fish.", 1800, 1, 2, new FishingPole(1, 1, LevelName.fiberGlass)),
    IridiumRod("Iridium Rod", "Use in the water to catch fish.", 7500, 1, 4, new FishingPole(1, 1, LevelName.iridium));
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer dailyLimit;
    private final Integer fishingSkill;
    private final Item item;
    FishShop(String name, String description, Integer price, Integer dailyLimit, Integer fishingSkill, Item item) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.fishingSkill = fishingSkill;
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
