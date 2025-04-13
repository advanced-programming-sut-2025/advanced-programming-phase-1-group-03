package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.Arrays;

public enum TreeFeatures {
    Apricot(SeedFeatures.Apricot, (ArrayList<Integer>)Arrays.asList(7,7,7,7), 28,
            FruitFeatures.Apricot, 1),
    ;

    private SeedFeatures source;
    private ArrayList<Integer> stages;
    private int totalHarvestTime;
    private FruitFeatures fruit;
    private int fruitHarvestCycle;

    TreeFeatures(SeedFeatures source, ArrayList<Integer> stages, int totalHarvestTime, FruitFeatures fruit,
                 int fruitHarvestCycle) {
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.fruitHarvestCycle = fruitHarvestCycle;
    }
}
