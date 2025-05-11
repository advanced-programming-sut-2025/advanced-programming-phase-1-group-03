package phi.ap.model.enums;

import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.List;

public enum TreeTypes {
    RegularSampleTree("T", Colors.fg(22), "", SeedTypes.RegularSampleTreeSeed, null, new ArrayList<>(List.of(7, 7, 7, 7)), 28,
            FruitTypes.RegularSampleFruit, Seasons.Special)//TODO : remove it;
    ;

    private Tile tile;
    private SeedTypes seedType;
    private SaplingTypes saplingType;
    private ArrayList<Integer> stages;
    private int totalHarvestTime;
    private FruitTypes fruit;
    private Seasons season;

    TreeTypes(String Stymbol, String fgColor, String bgColor, SeedTypes seedType, SaplingTypes saplingType, ArrayList<Integer> stages,
              int totalHarvestTime, FruitTypes fruit, Seasons season) {
        tile = new Tile(Stymbol, fgColor, bgColor);
        this.seedType = seedType;
        this.saplingType = saplingType;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.season = season;
    }

    public Tile getTile() {
        return tile;
    }

    public SeedTypes getSeedType() {
        return seedType;
    }

    public SaplingTypes getSaplingType() {
        return saplingType;
    }

    public ArrayList<Integer> getStages() {
        return new ArrayList<>(stages);
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public FruitTypes getFruit() {
        return fruit;
    }

    public Seasons getSeason() {
        return season;
    }
}
