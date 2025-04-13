package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FruitFeatures {
    Apricot(59, new Eatable(38), Seasons.buildList(Seasons.Spring)),
    Cherry(80, new Eatable(38), Seasons.buildList(Seasons.Spring)),
    Banana(150, new Eatable(75), Seasons.buildList(Seasons.Summer)),
    Mango(130, new Eatable(100), Seasons.buildList(Seasons.Summer)),
    Orange(100, new Eatable(38), Seasons.buildList(Seasons.Summer)),
    Peach(140, new Eatable(38), Seasons.buildList(Seasons.Summer)),
    Apple(100, new Eatable(38), Seasons.buildList(Seasons.Fall)),
    Pomegranate(140, new Eatable(38), Seasons.buildList(Seasons.Fall)),
    OakResin(150, null, Seasons.getAll()),
    MapleSyrup(200, null, Seasons.getAll()),
    PineTar(100, null, Seasons.getAll()),
    Sap(2, new Eatable(-2), Seasons.getAll()),
    CommonMushroom(40, new Eatable(38), Seasons.getAll()),
    MysticSyrup(1000, new Eatable(500), Seasons.getAll());

    private int baseSellPrice;
    private Eatable eatable;

    FruitFeatures(int baseSellPrice, Eatable eatable, ArrayList<Seasons> seasons) {
        this.baseSellPrice = baseSellPrice;
        this.eatable = eatable;
    }
}
