package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;

public enum FruitTypes {
    Apricot("Apricot", 59, new Eatable(38), Seasons.buildList(Seasons.Spring),1, 1),
    Cherry("Cherry", 80, new Eatable(38), Seasons.buildList(Seasons.Spring), 1, 1),
    Banana("Banana", 150, new Eatable(75), Seasons.buildList(Seasons.Summer), 1, 1),
    Mango("Mango", 130, new Eatable(100), Seasons.buildList(Seasons.Summer), 1, 1),
    Orange("Orange", 100, new Eatable(38), Seasons.buildList(Seasons.Summer), 1, 1),
    Peach("Peach", 140, new Eatable(38), Seasons.buildList(Seasons.Summer), 1, 1),
    Apple("Apple", 100, new Eatable(38), Seasons.buildList(Seasons.Fall), 1, 1),
    Pomegranate("Pomegranate", 140, new Eatable(38), Seasons.buildList(Seasons.Fall), 1, 1),
    OakResin("OakResin", 150, null, Seasons.getAll(), 7, 1),
    MapleSyrup("MapleSyrup", 200, null, Seasons.getAll(), 9, 1),
    PineTar("PineTar", 100, null, Seasons.getAll(), 5, 1),
    Sap("Sap", 2, new Eatable(-2), Seasons.getAll(), 1, 1),
    CommonMushroom("CommonMushroom", 40, new Eatable(38), Seasons.getAll(), 1, 1),
    MysticSyrup("MysticSyrup", 1000, new Eatable(500), Seasons.getAll(), 7, 1),
    RegularSampleFruit("RegularSampleFruit", 10, new Eatable(10), Seasons.getAll(), 1, 1);//TODO : remove it


    private final String name;
    private int baseSellPrice;
    private Eatable eatable;
    private int fruitHarvestCycle;
    private int stackSize;

    FruitTypes(String name, int baseSellPrice, Eatable eatable, ArrayList<Seasons> seasons, int fruitHarvestCycle, int stackSize) {
        this.baseSellPrice = baseSellPrice;
        this.eatable = eatable;
        this.name = name;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.stackSize = stackSize;
    }

    public String getName() {
        return name;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public Eatable getEatable() {
        return eatable;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public int getStackSize() {
        return stackSize;
    }
}
