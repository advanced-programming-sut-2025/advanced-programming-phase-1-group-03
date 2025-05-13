package phi.ap.model.enums;

import phi.ap.model.items.Item;
import phi.ap.model.items.products.AnimalProduct;

public enum AnimalProductTypes {
    ChickenEgg("ChickenEgg", 50),
    LargeChickenEgg("LargeChickenEgg", 95),
    DuckEgg("DuckEgg", 95),
    LargeDuckEgg("LargeDuckEgg", 250),
    Wool("Wool", 340),
    RabbitFoot("RabbitFoot", 565),
    DinosaurEgg("DinosaurEgg", 350),
    Milk("Milk", 125),
    LargeMilk("LargeMilk", 190),
    GoatMilk("GoatMilk", 225),
    LargeGoatMilk("LargeGoatMilk", 345),
    Cheese("Cheese", 340),
    Truffle("Truffle", 625)
    ;


    private final String name;
    private final int price;

    AnimalProductTypes(String name, int price) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static AnimalProductTypes getType(String name) {
        AnimalProductTypes animalProductType;
        try {
            animalProductType = AnimalProductTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return animalProductType;
    }

}
