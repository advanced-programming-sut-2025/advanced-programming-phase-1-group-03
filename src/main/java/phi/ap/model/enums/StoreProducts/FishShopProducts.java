package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.enums.LevelName;
import phi.ap.model.enums.ProductNames;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Product;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.tools.FishingPole;

public enum FishShopProducts implements StoreItemProducer {
    FishSmoker("Fish Smoker (Recipe)", "A recipe to make Fish Smoker", 10000, 1, 0, null){
        @Override
        public Item getItem() {
            Item p = CraftingTypes.FishSmoker.getRecipe();
            p.setSellPrice(getPrice());
            return p;
        }

        @Override
        public String getNameInStore() {
            return getName();
        }
    },
    TroutSoup("Trout Soup", "Pretty salty.", 250, 1, 0, null){
        @Override
        public Item getItem() {
            Item p =  new Product(ProductNames.TroutSoup);
            p.setSellPrice(getPrice());
            return p;
        }
        @Override
        public String getNameInStore() {
            return getName();
        }
    },
    BambooPole("Bamboo Pole", "Use in the water to catch fish.", 500, 1, 0, LevelName.bamboo){
        @Override
        public Item getItem() {
            Item p = getFishingPole();
            p.setSellPrice(getPrice());
            return p;
        }
        @Override
        public String getNameInStore() {
            return getName();
        }
    },
    TrainingRod("Training Rod", "It's a lot easier to use than other rods, but can only catch basic fish.", 25, 1, 0, LevelName.training){
        @Override
        public Item getItem() {
            Item p = getFishingPole();
            p.setSellPrice(getPrice());
            return p;
        }
        @Override
        public String getNameInStore() {
            return getName();
        }
    },
    FiberglassRod("Fiberglass Rod", "Use in the water to catch fish.", 1800, 1, 2,LevelName.fiberGlass){
        @Override
        public Item getItem() {
            Item p = getFishingPole();
            p.setSellPrice(getPrice());
            return p;
        }
        @Override
        public String getNameInStore() {
            return getName();
        }
    },
    IridiumRod("Iridium Rod", "Use in the water to catch fish.", 7500, 1, 4, LevelName.iridium){
        @Override
        public Item getItem() {
            Item p = getFishingPole();
            p.setSellPrice(getPrice());
            return p;
        }
        @Override
        public String getNameInStore() {
            return getName();
        }
    };
    private final String name;
    private final String description;
    private final Integer price;
    private int dailyLimit;
    private int availableAmount = 1000;
    private final Integer fishingSkill;
    private final LevelName level;
    private Item item;
    FishShopProducts(String name, String description, Integer price, Integer dailyLimit, Integer fishingSkill, LevelName level) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.level = level;
        this.fishingSkill = fishingSkill;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public abstract Item getItem();

    //if we use this method for non-fishing pole items throws exception
    public FishingPole getFishingPole() {
        FishingPole fishingPole = new FishingPole();
        fishingPole.getLevelProcess().setCurrentLevel(level);
        return fishingPole;
    }

    public int getFishingSkill() {
        return fishingSkill;
    }

    public static FishShopProducts fromString(String string) {
        for(FishShopProducts product : FishShopProducts.values())
            if(product.name.equalsIgnoreCase(string))
                return product;
        return null;
    }
}
