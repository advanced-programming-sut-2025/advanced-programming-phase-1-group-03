package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Coordinate;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.Result;
import phi.ap.model.enums.BuildingTypes;
import phi.ap.model.enums.StoneTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.*;
import phi.ap.model.items.products.Stone;
import phi.ap.model.items.products.Wood;

public enum CarpenterShopProducts implements StoreItemProducer{
    Wood("Wood", "A sturdy, yet flexible plant material with a wide variety of uses.", 10,
            null, null, null, null, Integer.MAX_VALUE){
        @Override
        public Item getItem() {
            return new Wood(1,1);
        }
    },
    Stone("Stone", "A sturdy, yet flexible plant material with a wide variety of uses.", 20,
            null, null, null, null, Integer.MAX_VALUE) {
        @Override
        public Item getItem() {
            return new Stone(1, 1, StoneTypes.RegularStone);
        }

    },
    Barn("Barn", "Houses 4 barn-dwelling animals.", 6000, 350, 150,
            7, 4, 1) {
        @Override
        public Item getItem() {
            AnimalHouse animalHouse = new AnimalHouse(getHeight(), getWidth(), BuildingTypes.Barn, null);
            animalHouse.setSellPrice(getPrice());
            return animalHouse;
        }

    },
    BigBarn("Big Barn", "Houses 8 barn-dwelling animals. Unlocks goats.", 12000, 450,
            200, 7, 4, 1) {
        @Override
        public Item getItem() {
            AnimalHouse animalHouse = new AnimalHouse(getHeight(), getWidth(), BuildingTypes.BigBarn, null);
            animalHouse.setSellPrice(getPrice());
            return animalHouse;
        }
    },
    DeluxeBarn("Deluxe Barn", "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.", 25000,
            550, 300, 7, 4, 1) {
        @Override
        public Item getItem() {
            AnimalHouse animalHouse =   new AnimalHouse(getHeight(), getWidth(), BuildingTypes.DeluxeBarn, null);
            animalHouse.setSellPrice(getPrice());
            return animalHouse;
        }

    },
    Coop("Coop", "Houses 4 coop-dwelling animals.", 4000, 300, 100,
            6, 3, 1) {
        @Override
        public Item getItem() {
            AnimalHouse animalHouse = new AnimalHouse(getHeight(), getWidth(), BuildingTypes.Coop, null);
            animalHouse.setSellPrice(getPrice());
            return animalHouse;
        }

    },
    BigCoop("Big Coop", "Houses 8 coop-dwelling animals. Unlocks ducks.", 10000, 400,
            150, 6, 3, 1) {
        @Override
        public Item getItem() {
            AnimalHouse animalHouse = new AnimalHouse(getHeight(), getWidth(), BuildingTypes.BigCoop,null);
            animalHouse.setSellPrice(getPrice());
            return animalHouse;
        }

    },
    DeluxeCoop("Deluxe Coop", "Houses 12 coop-dwelling animals. Unlocks rabbits.", 20000,
            500, 200, 6, 3, 1) {
        @Override
        public Item getItem() {
            AnimalHouse animalHouse = new AnimalHouse(getHeight(), getWidth(), BuildingTypes.DeluxeCoop, null);
            animalHouse.setSellPrice(getPrice());
            return animalHouse;
        }

    },
    Well("Well", "Provides a place for you to refill your watering can.", 1000, 0,
            75, 3, 3, 1) {
        @Override
        public Item getItem() {
            Well well = new Well(getHeight(), getWidth());
            well.setSellPrice(getPrice());
            return well;
        }
    },
    ShippingBin("Shipping Bin", "Items placed in it will be included in the nightly shipment.",
            250, 150, 0, 1, 1, Integer.MAX_VALUE) {
        @Override
        public Item getItem() {
            ShippingBin shippingBin = new ShippingBin(getHeight(), getWidth());
            shippingBin.setSellPrice(getPrice());
            return shippingBin;
        }
    };
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer neededWood;
    private final Integer neededStone;
    private final Integer height;
    private final Integer width;
    private Integer dailyLimit;

    CarpenterShopProducts(String name, String description, Integer price, Integer neededWood, Integer neededStone,
                          Integer height, Integer width, Integer dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.neededWood = neededWood;
        this.neededStone = neededStone;
        this.height = height;
        this.width = width;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getNeededWood() {
        return neededWood;
    }

    public Integer getHeight() {
        return height;
    }

    @Override
    public String getNameInStore() {
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
    public static CarpenterShopProducts fromString(String string) {
        for(CarpenterShopProducts product : CarpenterShopProducts.values())
            if(product.name.equalsIgnoreCase(string))
                return product;
        return null;
    }

    public Integer getNeededStone() {
        return neededStone;
    }
}
