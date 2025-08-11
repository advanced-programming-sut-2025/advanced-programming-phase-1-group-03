package com.ap.client.model;

import com.ap.client.items.ItemNames;

public enum AnimalProducts {
    Milk("Milk", "milk", 125, ItemNames.AnimalProduct),
    LargeMilk("Large_Milk", "large milk", 190, ItemNames.AnimalProduct),
    GoatMilk("Goat_Milk", "goat milk", 225, ItemNames.AnimalProduct),
    LargeGoatMilk("Large_Goat_Milk", "large goat milk", 345, ItemNames.AnimalProduct),
    Wool("Wool", "wool", 340, ItemNames.AnimalProduct),
    Truffle("Truffle", "truffle", 625, ItemNames.AnimalProduct),
    Egg("Egg", "egg", 50, ItemNames.AnimalProduct),
    LargeEgg("Large_Egg", "large egg", 95, ItemNames.AnimalProduct),
    VoidEgg("Void_Egg", "void egg", 800, ItemNames.AnimalProduct),
    BrownEgg("Brown_Egg", "brown egg", 150, ItemNames.AnimalProduct),
    LargeBrownEgg("Large_Brown_Egg", "large brown egg", 250, ItemNames.AnimalProduct),
    GoldenEgg("Golden_Egg", "golden egg", 1000, ItemNames.AnimalProduct),
    DuckEgg("Duck_Egg", "duck egg", 95, ItemNames.AnimalProduct),
    DuckFeather("Duck_Feather", "duck feather", 250, ItemNames.AnimalProduct),
    OstrichEgg("Ostrich_Egg", "ostrich egg", 350, ItemNames.AnimalProduct),
    RabbitFoot("Rabbit_Foot", "rabbit foot", 565, ItemNames.AnimalProduct),
    DinosaurEgg("Dinosaur_Egg", "dinosaur egg", 450, ItemNames.AnimalProduct)
    ;

    AnimalProducts(String atlasKey, String uiName, int price, ItemNames itemName) {
        this.atlasKey = atlasKey;
        this.uiName = uiName;
        this.price = price;
    }

    private String atlasKey;
    private String uiName;
    private int price;
    private ItemNames itemNames;

    public String getAtlasKey() {
        return atlasKey;
    }

    public String getUiName() {
        return uiName;
    }

    public int getPrice() {
        return price;
    }

    public ItemNames getItemNames() {
        return itemNames;
    }
}
