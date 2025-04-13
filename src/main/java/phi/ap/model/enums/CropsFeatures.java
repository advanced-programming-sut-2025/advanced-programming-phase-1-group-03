package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.Arrays;

public enum CropsFeatures {
    BlueJazz("Blue Jazz",SeedFeatures.Jazz, (ArrayList<Integer>) Arrays.asList(1, 2, 2, 2), 7,true,, 50, true, 45, Seasons.Spring, false);
    private String name;
    private SeedFeatures source;
    private ArrayList<Integer> stage;
    private int harvestTime;
    private boolean onTime;
    private int regrowthTime;
    private int baseSellPrice;
    private Eatable eatable;
    private int energy;
    private Seasons season;
    private boolean canBecomeGiant;

    CropsFeatures(String name, SeedFeatures source, ArrayList<Integer> stage, int harvestTime, boolean onTime, int regrowthTime, int baseSellPrice, Eatable eatable, int energy, Seasons season, boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
        this.harvestTime = harvestTime;
        this.onTime = onTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.eatable = eatable;
        this.energy = energy;
        this.season = season;
        this.canBecomeGiant = canBecomeGiant;
        this.stage = stage;
    }
}
