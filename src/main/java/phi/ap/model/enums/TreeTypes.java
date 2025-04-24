package phi.ap.model.enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum TreeTypes {
    ;

    private SeedTypes source;
    private ArrayList<Integer> stages;
    private int totalHarvestTime;
    private FruitTypes fruit;
    private int fruitHarvestCycle;

    TreeTypes(SeedTypes source, ArrayList<Integer> stages, int totalHarvestTime, FruitTypes fruit,
              int fruitHarvestCycle) {
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.fruitHarvestCycle = fruitHarvestCycle;
    }
}
