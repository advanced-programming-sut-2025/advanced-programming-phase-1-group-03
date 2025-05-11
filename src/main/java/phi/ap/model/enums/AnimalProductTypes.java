package phi.ap.model.enums;

public enum AnimalProductTypes {
    ChickenEgg("Chicken Egg", 50),
    LargeChickenEgg("Large Chicken Egg", 95),
    DuckEgg("Duck Egg", 95),
    LargeDuckEgg("Large Duck Egg", 250),
    Wool("Wool", 340),
    RabbitFoot("Rabbit Foot", 565),
    DinosaurEgg("Dinosaur Egg", 350),
    Milk("Milk", 125),
    LargeMilk("Large Milk", 190),
    GoatMilk("Goat Milk", 225),
    LargeGoatMilk("Large Goat Milk", 345),
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

}
