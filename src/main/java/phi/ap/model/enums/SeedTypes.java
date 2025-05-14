package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.List;

public enum SeedTypes {
    JazzSeeds(":", Colors.fg(160), "", Seasons.Spring),
    CarrotSeeds(":", Colors.fg(160), "", Seasons.Spring),
    CauliflowerSeeds(":", Colors.fg(160), "", Seasons.Spring),
    CoffeeBean(":", Colors.fg(160), "", Seasons.Spring),
    GarlicSeeds(":", Colors.fg(160), "", Seasons.Spring),
    BeanStarter(":", Colors.fg(160), "", Seasons.Spring),
    KaleSeeds(":", Colors.fg(160), "", Seasons.Spring),
    ParsnipSeeds(":", Colors.fg(160), "", Seasons.Spring),
    PotatoSeeds(":", Colors.fg(160), "", Seasons.Spring),
    RhubarbSeeds(":", Colors.fg(160), "", Seasons.Spring),
    StrawberrySeeds(":", Colors.fg(160), "", Seasons.Spring),
    TulipBulb(":", Colors.fg(160), "", Seasons.Spring),
    RiceShoot(":", Colors.fg(160), "", Seasons.Spring),
    BlueberrySeeds(":", Colors.fg(160), "", Seasons.Summer),
    CornSeeds(":", Colors.fg(160), "", Seasons.Summer),
    HopsStarter(":", Colors.fg(160), "", Seasons.Summer),
    PepperSeeds(":", Colors.fg(160), "", Seasons.Summer),
    MelonSeeds(":", Colors.fg(160), "", Seasons.Summer),
    PoppySeeds(":", Colors.fg(160), "", Seasons.Summer),
    RadishSeeds(":", Colors.fg(160), "", Seasons.Summer),
    RedCabbageSeeds(":", Colors.fg(160), "", Seasons.Summer),
    StarfruitSeeds(":", Colors.fg(160), "", Seasons.Summer),
    SpangleSeeds(":", Colors.fg(160), "", Seasons.Summer),
    SummerSquashSeeds(":", Colors.fg(160), "", Seasons.Summer),
    SunflowerSeeds(":", Colors.fg(160), "", Seasons.Summer),
    TomatoSeeds(":", Colors.fg(160), "", Seasons.Summer),
    WheatSeeds(":", Colors.fg(160), "", Seasons.Summer),
    AmaranthSeeds(":", Colors.fg(160), "", Seasons.Fall),
    ArtichokeSeeds(":", Colors.fg(160), "", Seasons.Fall),
    BeetSeeds(":", Colors.fg(160), "", Seasons.Fall),
    BokChoySeeds(":", Colors.fg(160), "", Seasons.Fall),
    BroccoliSeeds(":", Colors.fg(160), "", Seasons.Fall),
    CranberrySeeds(":", Colors.fg(160), "", Seasons.Fall),
    EggplantSeeds(":", Colors.fg(160), "", Seasons.Fall),
    FairySeeds(":", Colors.fg(160), "", Seasons.Fall),
    GrapeStarter(":", Colors.fg(160), "", Seasons.Fall),
    PumpkinSeeds(":", Colors.fg(160), "", Seasons.Fall),
    YamSeeds(":", Colors.fg(160), "", Seasons.Fall),
    RareSeed(":", Colors.fg(160), "", Seasons.Fall),
    PowdermelonSeeds(":", Colors.fg(160), "", Seasons.Winter),
    AncientSeeds(":", Colors.fg(160), "", Seasons.Special),
    MixedSeeds(":", Colors.fg(160), "", Seasons.Special),
    RegularSampleTreeSeed(":", Colors.fg(160), "", Seasons.Special),
    //Foraging tree seeds
    Acorns(":", Colors.fg(160), "", Seasons.Special),
    MapleSeeds(":", Colors.fg(160), "", Seasons.Special),
    PineCones(":", Colors.fg(160), "", Seasons.Special),
    MahoganySeeds(":", Colors.fg(160), "", Seasons.Special),
    MushroomTreeSeeds(":", Colors.fg(160), "", Seasons.Special),
    MysticTreeSeeds(":", Colors.fg(160), "", Seasons.Special);
    ;
    private Seasons season;
    private Tile tile;
    SeedTypes(String symbol, String fgColor, String bgColor,Seasons season) {
        this.tile = new Tile(symbol, fgColor, bgColor);
        this.season = season;
    }

    public Tile getTile() {
        return tile;
    }

    public Seasons getSeason() {
        return season;
    }

    public ArrayList<Seasons> getSeasonList() {
        if (season != Seasons.Special) return new ArrayList<>(List.of(season));
        return new ArrayList<>(List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));
    }

    public static SeedTypes getRandomFromSeason(Seasons season) {
        SeedTypes result = null;
        do {
            int ind = App.getInstance().getRandomNumber(0, SeedTypes.values().length - 1);
            result = SeedTypes.values()[ind];
        } while (result.season != season && result.season != Seasons.Special);
        return result;
    }

    public CropsTypes findCropType() {
        if (this == SeedTypes.MixedSeeds) {
            ArrayList<SeedTypes> seeds = switch (Game.getInstance().getDate().getSeason()) {
                case Seasons.Special -> new ArrayList<>(MixedSeedsTypes.SpringMixedSeeds.getMixedSeeds());
                case Seasons.Summer -> new ArrayList<>(MixedSeedsTypes.SummerMixedSeeds.getMixedSeeds());
                case Seasons.Fall -> new ArrayList<>(MixedSeedsTypes.FallMixedSeeds.getMixedSeeds());
                case Seasons.Winter -> new ArrayList<>(MixedSeedsTypes.WinterMixedSeeds.getMixedSeeds());
                default -> null;
            };
            if (seeds == null) return null;
            int rand = App.getInstance().getRandomNumber(0, seeds.size() - 1);
            return seeds.get(rand).findCropType();
        }
        for (CropsTypes value : CropsTypes.values()) {
            if (value.getSeed() == this) {
                return value;
            }
        }
        return null;
    }


    public static SeedTypes find(String name) {
        name = name.toLowerCase().replaceAll("\\s+", "");
        for (SeedTypes value : values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public static SeedTypes getType(String name) {
        SeedTypes seedType;
        try {
            seedType = SeedTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return seedType;
    }
}
