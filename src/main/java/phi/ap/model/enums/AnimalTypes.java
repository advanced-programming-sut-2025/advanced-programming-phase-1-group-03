package phi.ap.model.enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum AnimalTypes {
    Chicken("Chicken",800,1, new ArrayList<>(Arrays.asList(AnimalProductTypes.ChickenEgg,
            AnimalProductTypes.LargeChickenEgg))),
    Cow("Cow", 1200,2, new ArrayList<>(Arrays.asList(AnimalProductTypes.Milk,
            AnimalProductTypes.LargeMilk))),
    Goat("Goat", 8000,4, new ArrayList<>(Arrays.asList(AnimalProductTypes.GoatMilk,
            AnimalProductTypes.LargeGoatMilk))),
    Duck("Duck", 14000,7, new ArrayList<>(Arrays.asList(AnimalProductTypes.DuckEgg,
            AnimalProductTypes.LargeDuckEgg))),
    Sheep("Sheep", 1500,1, new ArrayList<>(Arrays.asList(AnimalProductTypes.Wool))),
    Rabbit("Rabbit", 4000,2, new ArrayList<>(Arrays.asList(AnimalProductTypes.RabbitFoot,
            AnimalProductTypes.Wool))),
    Dinosaur("Dinosaur", 8000,3, new ArrayList<>(Arrays.asList(AnimalProductTypes.DinosaurEgg))),
    Pig("Pig", 16000, Integer.MAX_VALUE, new ArrayList<>(Arrays.asList(AnimalProductTypes.Truffle)));


    private final String name;
    private final int price;
    private final int dayToProduce;
    ArrayList<AnimalProductTypes> animalProductTypes;

    AnimalTypes(String name, int price, int dayToProduce, ArrayList<AnimalProductTypes> animalProductTypes) {
        this.price = price;
        this.name = name;
        this.dayToProduce = dayToProduce;
        this.animalProductTypes = animalProductTypes;;
    }

    public int getDayToProduce() {
        return dayToProduce;
    }

    public String getName() {
        return name;
    }

    public ArrayList<AnimalProductTypes> getAnimalProductTypes() {
        return animalProductTypes;
    }

    public int getPrice() {
        return price;
    }
}
