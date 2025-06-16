package phi.ap.model.enums;

import phi.ap.model.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public enum AnimalTypes {
    Chicken(new Tile("A", Colors.fg(196), ""), "Chicken",800,1, new ArrayList<>(Arrays.asList(AnimalProductTypes.ChickenEgg,
            AnimalProductTypes.LargeChickenEgg))),
    Cow(new Tile("A", Colors.fg(196), ""),"Cow", 1200,1, new ArrayList<>(Arrays.asList(AnimalProductTypes.Milk,
            AnimalProductTypes.LargeMilk))),
    Goat(new Tile("A", Colors.fg(196), ""),"Goat", 8000,4, new ArrayList<>(Arrays.asList(AnimalProductTypes.GoatMilk,
            AnimalProductTypes.LargeGoatMilk))),
    Duck(new Tile("A", Colors.fg(196), ""),"Duck", 14000,7, new ArrayList<>(Arrays.asList(AnimalProductTypes.DuckEgg,
            AnimalProductTypes.LargeDuckEgg))),
    Sheep(new Tile("A", Colors.fg(196), ""),"Sheep", 1500,1, new ArrayList<>(Arrays.asList(AnimalProductTypes.Wool))),
    Rabbit(new Tile("A", Colors.fg(196), ""),"Rabbit", 4000,2, new ArrayList<>(Arrays.asList(AnimalProductTypes.RabbitFoot,
            AnimalProductTypes.Wool))),
    Dinosaur(new Tile("A", Colors.fg(196), ""),"Dinosaur", 8000,3, new ArrayList<>(Arrays.asList(AnimalProductTypes.DinosaurEgg))),
    Pig(new Tile("A", Colors.fg(196), ""),"Pig", 16000, Integer.MAX_VALUE, new ArrayList<>(Arrays.asList(AnimalProductTypes.Truffle)));

    private final String name;
    private final int price;
    private final int dayToProduce;
    ArrayList<AnimalProductTypes> animalProductTypes;
    private Tile tile;
    AnimalTypes(Tile tile, String name, int price, int dayToProduce, ArrayList<AnimalProductTypes> animalProductTypes) {
        this.price = price;
        this.name = name;
        this.dayToProduce = dayToProduce;
        this.animalProductTypes = animalProductTypes;;
        this.tile = tile;
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

    public static AnimalTypes getType(String name) {
        AnimalTypes animalType;
        try {
            animalType = AnimalTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return animalType;
    }
}
