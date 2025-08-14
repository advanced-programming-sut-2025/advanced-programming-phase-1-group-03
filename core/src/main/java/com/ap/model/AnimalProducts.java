package com.ap.model;


public enum AnimalProducts {
    Milk("Milk", "milk", 125),
    LargeMilk("Large_Milk", "large milk", 190),
    GoatMilk("Goat_Milk", "goat milk", 225),
    LargeGoatMilk("Large_Goat_Milk", "large goat milk", 345),
    Wool("Wool", "wool", 340),
    Truffle("Truffle", "truffle", 625),
    Egg("Egg", "egg", 50),
    LargeEgg("Large_Egg", "large egg", 95),
    VoidEgg("Void_Egg", "void egg", 800),
    BrownEgg("Brown_Egg", "brown egg", 150),
    LargeBrownEgg("Large_Brown_Egg", "large brown egg", 250),
    GoldenEgg("Golden_Egg", "golden egg", 1000),
    DuckEgg("Duck_Egg", "duck egg", 95),
    DuckFeather("Duck_Feather", "duck feather", 250),
    OstrichEgg("Ostrich_Egg", "ostrich egg", 350),
    RabbitFoot("Rabbit_Foot", "rabbit foot", 565),
    DinosaurEgg("Dinosaur_Egg", "dinosaur egg", 450)
    ;

    AnimalProducts(String atlasKey, String uiName, int price) {
        this.atlasKey = atlasKey;
        this.uiName = uiName;
        this.price = price;
    }

    private String atlasKey;
    private String uiName;
    private int price;

    public String getAtlasKey() {
        return atlasKey;
    }

    public String getUiName() {
        return uiName;
    }

    public int getPrice() {
        return price;
    }

}
