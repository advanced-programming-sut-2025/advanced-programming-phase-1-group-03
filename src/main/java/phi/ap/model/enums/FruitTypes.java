package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;

public enum FruitTypes {
    Apricot("Apricot", 59, new Eatable(38), Seasons.buildList(Seasons.Spring)),
    Cherry("Cherry", 80, new Eatable(38), Seasons.buildList(Seasons.Spring)),
    Banana("Banana", 150, new Eatable(75), Seasons.buildList(Seasons.Summer)),
    Mango("Mango", 130, new Eatable(100), Seasons.buildList(Seasons.Summer)),
    Orange("Orange", 100, new Eatable(38), Seasons.buildList(Seasons.Summer)),
    Peach("Peach", 140, new Eatable(38), Seasons.buildList(Seasons.Summer)),
    Apple("Apple", 100, new Eatable(38), Seasons.buildList(Seasons.Fall)),
    Pomegranate("Pomegranate", 140, new Eatable(38), Seasons.buildList(Seasons.Fall)),
    OakResin("OakResin", 150, null, Seasons.getAll()),
    MapleSyrup("MapleSyrup", 200, null, Seasons.getAll()),
    PineTar("PineTar", 100, null, Seasons.getAll()),
    Sap("Sap", 2, new Eatable(-2), Seasons.getAll()),
    CommonMushroom("CommonMushroom", 40, new Eatable(38), Seasons.getAll()),
    MysticSyrup("MysticSyrup", 1000, new Eatable(500), Seasons.getAll()),
    RegularSampleFruit("RegularSampleFruit", 10, new Eatable(10), Seasons.getAll());//TODO : remove it

    private final String name;
    private int baseSellPrice;
    private Eatable eatable;

    FruitTypes(String name, int baseSellPrice, Eatable eatable, ArrayList<Seasons> seasons) {
        this.baseSellPrice = baseSellPrice;
        this.eatable = eatable;
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
