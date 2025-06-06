package phi.ap.model.enums;

import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.List;

public enum TreeTypes {
    RegularSampleTree("T",
            Colors.fg(28),
            "",
            SeedTypes.RegularSampleTreeSeed,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.RegularSampleFruit,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))), //TODO : remove it
    ApricotTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.ApricotSapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Apricot,
            Seasons.Spring,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    CherryTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.CherrySapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Cherry,
            Seasons.Spring,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    BananaTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.BananaSapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Banana,
            Seasons.Summer,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    MangoTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.MangoSapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Mango,
            Seasons.Summer,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    PeachTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.PeachSapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Peach,
            Seasons.Summer,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    AppleTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.AppleSapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Mango,
            Seasons.Fall,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    PomegranateTree("T",
            Colors.fg(28),
            "",
            null,
            SaplingTypes.PomegranateSapling,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Pomegranate,
            Seasons.Fall,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    OakTree("T",
            Colors.fg(28),
            "",
            SeedTypes.Acorns,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.OakResin,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    MapleTree("T",
            Colors.fg(28),
            "",
            SeedTypes.MapleSeeds,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.MapleSyrup,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    PineTree("T",
            Colors.fg(28),
            "",
            SeedTypes.PineCones,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.PineTar,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    MahoganyTree("T",
            Colors.fg(28),
            "",
            SeedTypes.MahoganySeeds,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.Sap,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    MushroomTree("T",
            Colors.fg(28),
            "",
            SeedTypes.MushroomTreeSeeds,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.CommonMushroom,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    MysticTree("T",
            Colors.fg(28),
            "",
            SeedTypes.MysticTreeSeeds,
            null,
            new ArrayList<>(List.of(7, 7, 7, 7)),
            FruitTypes.MysticSyrup,
            Seasons.Special,
            new ArrayList<>(List.of(new Tile("t", Colors.fg(185), ""), new Tile("t", Colors.fg(196), ""), new Tile("t", Colors.fg(190), ""), new Tile("T", Colors.fg(136), ""), new Tile("T", Colors.fg(28), "")))),
    ;

    private Tile tile;
    private SeedTypes seedType;
    private SaplingTypes saplingType;
    private ArrayList<Integer> stages;
    private FruitTypes fruit;
    private Seasons season;
    private ArrayList<Tile> shapeAtStages;

    TreeTypes(String Stymbol,
              String fgColor,
              String bgColor,
              SeedTypes seedType,
              SaplingTypes saplingType,
              ArrayList<Integer> stages,
              FruitTypes fruit,
              Seasons season,
              ArrayList<Tile> shapeAtStages) {
        tile = new Tile(Stymbol, fgColor, bgColor);
        this.seedType = seedType;
        this.saplingType = saplingType;
        this.stages = stages;
        this.fruit = fruit;
        this.season = season;
        this.shapeAtStages = shapeAtStages;

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

    public FruitTypes getFruit() {
        return fruit;
    }

    public Seasons getSeason() {
        return season;
    }

    public ArrayList<Tile> getShapeAtStages() {
        return new ArrayList<>(shapeAtStages);
    }

    public ArrayList<Seasons> getSeasonList() {
        if (season != Seasons.Special) return new ArrayList<>(List.of(season));
        return new ArrayList<>(List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));
    }

    public static TreeTypes find(String name) {
        name = name.toLowerCase().replaceAll("\\s+", "");
        for (TreeTypes value : values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public static TreeTypes findBySapling(SaplingTypes sapling) {
        if (sapling == null) return null;
        for (TreeTypes value : values()) {
            if (sapling.equals(value.getSaplingType())) return value;
        }
        return null;
    }

    public static TreeTypes findBySeed(SeedTypes seed) {
        if (seed == null) return null;
        for (TreeTypes value : values()) {
            if (seed.equals(value.getSeedType())) return value;
        }
        return null;
    }

    public static TreeTypes getType(String name) {
        TreeTypes treeType;
        try {
            treeType = TreeTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return treeType;
    }
}
