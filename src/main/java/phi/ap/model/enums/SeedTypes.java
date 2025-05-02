package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

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
    PowdermelonSeeds(":", Colors.fg(160), "", Seasons.Fall),//TODO wrong seasons;
    AncientSeeds(":", Colors.fg(160), "", Seasons.Winter),
    MixedSeeds(":", Colors.fg(160), "", Seasons.Winter),
    RegularSampleTreeSeed(":", Colors.fg(160), "", Seasons.Special);
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

    public static SeedTypes getRandomFromSeason(Seasons season) {
        SeedTypes result = null;
        do {
            int ind = App.getInstance().getRandomNumber(0, SeedTypes.values().length - 1);
            result = SeedTypes.values()[ind];
        } while (result.season != season && result.season != Seasons.Special);
        return result;
    }
}
