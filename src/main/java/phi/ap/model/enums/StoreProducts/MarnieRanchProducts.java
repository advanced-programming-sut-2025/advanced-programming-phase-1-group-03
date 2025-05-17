package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.AnimalTypes;
import phi.ap.model.enums.BuildingTypes;
import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.AnimalHouse;
import phi.ap.model.items.Animal;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.tools.MilkPail;
import phi.ap.model.items.tools.Shear;

import java.util.ArrayList;

public enum MarnieRanchProducts  implements StoreItemProducer{
    MilkPay("Milk Pail", "Gather milk from your animals.",	1000,
            1, new MilkPail(), StoreTypes.MarnieRanch, null),
    Shear("Shears", "Use this to collect wool from sheep", 	1000,
            1, new Shear(), StoreTypes.MarnieRanch, null),
    Hay("Hay", "Dried grass used as animal food.", 50, Integer.MAX_VALUE,
            new Food(1, 1, FoodTypes.Hay), StoreTypes.MarnieRanch, null),
    // Animals
    Chicken("Chicken", "Well cared-for chickens lay eggs every day. Lives in the coop.",
            800, 2, new Animal(AnimalTypes.Chicken, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.Coop),
    Cow("Cow", "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn.",
            1500, 2, new Animal(AnimalTypes.Cow, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.Barn),
    Goat("Goat", "Happy provide goat milk every other day. A milk pail is required to harvest the milk." +
            " Lives in the barn.", 4000,2,new Animal(AnimalTypes.Goat, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.BigBarn),
    Duck("Duck", "Happy lay duck eggs every other day. Lives in the coop.", 1200, 2,
            new Animal(AnimalTypes.Duck, 1, 1),  StoreTypes.MarnieRanch, BuildingTypes.BigCoop),
    Sheep("Sheep", "Can be shorn for wool. A pair of shears is required to harvest the wool. " +
            "Lives in the barn.", 8000,2,new Animal(AnimalTypes.Sheep, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.DeluxeBarn),
    Rabbit("Rabbit", "These are wooly rabbits! They shed precious wool every few days. Lives " +
            "in the coop.", 8000, 2,new Animal(AnimalTypes.Rabbit, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.DeluxeCoop),
    Dinosaur("Dinosaur", "The Dinosaur is a farm animal that lives in a Big Coop.",
            14000, 2,new Animal(AnimalTypes.Dinosaur, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.BigCoop),
    Pig("Pig", "These pigs are trained to find truffles! Lives in the barn.",
            16000, 2,new Animal(AnimalTypes.Pig, 1, 1),
            StoreTypes.MarnieRanch, BuildingTypes.DeluxeBarn);

    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;
    private final Item item;
    private final StoreTypes storeType;
    private final BuildingTypes buildingType;

    MarnieRanchProducts(String name, String description, Integer price, Integer dailyLimit,
                        Item item, StoreTypes storeType, BuildingTypes buildingTypes) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.storeType = storeType;
        this.buildingType = buildingTypes;
    }

    public static Result<String> buyAnimal(String productName, String name) {
        int amount = 1;
        MarnieRanchProducts marnieRanchProducts;
        try {
            marnieRanchProducts = MarnieRanchProducts.valueOf(productName);
        }
        catch (IllegalArgumentException e) {
            return new Result<>(false, "There is no product with this name.");
        }
        if(amount > marnieRanchProducts.availableAmount) {
            return new Result<>(false, "There is not enough amount of this product.");
        }
        else if(amount > marnieRanchProducts.dailyLimit) {
            return new Result<>(false, "You can't purchase this amount of product on this day.");
        }
        else if(amount * marnieRanchProducts.price > Game.getInstance().getCurrentPlayer().getGold()) {
            return new Result<>(false, "You don't have enough money.");
        }
        Animal animal = new Animal(((Animal)marnieRanchProducts.item).getType(),
                marnieRanchProducts.item.getHeight(), marnieRanchProducts.item.getWidth());
        ArrayList<AnimalHouse> animalHouse = Game.getInstance().getCurrentPlayer().getOwnedAnimalHouse();
        boolean ok = false;
        for(AnimalHouse animalHouse1 : animalHouse) {
            System.out.println(animalHouse1);
            if(animalHouse1.getBuildingType().equals(marnieRanchProducts.buildingType) && animalHouse1.addAnimal(animal))
                ok = true;
        }
        if(!ok)
            return new Result<>(false, "You don't have " +
                    marnieRanchProducts.buildingType + " to keep " + animal.getType() + ".");
        marnieRanchProducts.availableAmount -= amount;
        marnieRanchProducts.dailyLimit -= amount;
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() -
                amount * marnieRanchProducts.price);
        animal.setName(name);
        ArrayList<Animal> animals = Game.getInstance().getCurrentPlayer().getAnimals();
        animals.add(animal);
        return new Result<>(true, "Item purchased successfully");
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

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public String getNameInStore() {
        return name;
    }
}
