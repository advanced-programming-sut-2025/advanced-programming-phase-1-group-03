package phi.ap.model.enums;

import phi.ap.model.Tile;

import java.util.ArrayList;
import java.util.List;

public enum TreeTypes {
    RegularSampleTree("T", Colors.fg(22), "", SeedTypes.RegularSampleTreeSeed, new ArrayList<>(List.of(7, 7, 7, 7)), 28,
            FruitTypes.RegularSampleFruit, 1)//TODO : remove it;
    ;

    private Tile tile;
    private SeedTypes source;
    private ArrayList<Integer> stages;
    private int totalHarvestTime;
    private FruitTypes fruit;
    private int fruitHarvestCycle;

    TreeTypes(String Stymbol, String fgColor, String bgColor, SeedTypes source, ArrayList<Integer> stages, int totalHarvestTime, FruitTypes fruit,
              int fruitHarvestCycle) {
        tile = new Tile(Stymbol, fgColor, bgColor);
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.fruitHarvestCycle = fruitHarvestCycle;
    }

    public Tile getTile() {
        return tile;
    }
}
