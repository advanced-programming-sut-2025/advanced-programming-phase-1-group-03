package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;

public enum FruitTypes {
    Apricot("Apricot", false,59, new Eatable(38), Seasons.buildList(Seasons.Spring),1, 1),
    Cherry("Cherry", false,80, new Eatable(38), Seasons.buildList(Seasons.Spring), 1, 1),
    Banana("Banana", false,150, new Eatable(75), Seasons.buildList(Seasons.Summer), 1, 1),
    Mango("Mango", false,130, new Eatable(100), Seasons.buildList(Seasons.Summer), 1, 1),
    Orange("Orange", false,100, new Eatable(38), Seasons.buildList(Seasons.Summer), 1, 1),
    Peach("Peach", false,140, new Eatable(38), Seasons.buildList(Seasons.Summer), 1, 1),
    Apple("Apple", false,100, new Eatable(38), Seasons.buildList(Seasons.Fall), 1, 1),
    Pomegranate("Pomegranate", false,140, new Eatable(38), Seasons.buildList(Seasons.Fall), 1, 1),
    OakResin("OakResin", false,150, null, Seasons.getAll(), 7, 1),
    MapleSyrup("MapleSyrup", true,200, null, Seasons.getAll(), 9, 1),
    PineTar("PineTar", false,100, null, Seasons.getAll(), 5, 1),
    Sap("Sap", false,2, new Eatable(-2), Seasons.getAll(), 1, 1),
    CommonMushroom("CommonMushroom", false,40, new Eatable(38), Seasons.getAll(), 1, 1),
    MysticSyrup("MysticSyrup", true,1000, new Eatable(500), Seasons.getAll(), 7, 1),
    RegularSampleFruit("RegularSampleFruit", false,10, new Eatable(10), Seasons.getAll(), 1, 1);//TODO : remove it


    private final String name;
    private int baseSellPrice;
    private Eatable eatable;
    private int fruitHarvestCycle;
    private int stackSize;
    private boolean isSyrup;
    private ArrayList<Seasons> seasons;

    FruitTypes(String name, boolean isSyrup,int baseSellPrice, Eatable eatable, ArrayList<Seasons> seasons, int fruitHarvestCycle, int stackSize) {
        this.isSyrup = isSyrup;
        this.baseSellPrice = baseSellPrice;
        this.eatable = eatable;
        this.name = name;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.stackSize = stackSize;
        this.seasons = seasons;
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

    public boolean isSyrup() {
        return isSyrup;
    }

    public static FruitTypes find(String name) {
        name = name.toLowerCase();
        for (FruitTypes value : FruitTypes.values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public TreeTypes getTreeType() {
        for (TreeTypes value : TreeTypes.values()) {
            if (value.getFruit() == this) return value;
        }
        return null;
    }

    public ArrayList<Seasons> getSeasonList() {
        return seasons;
    }
}
