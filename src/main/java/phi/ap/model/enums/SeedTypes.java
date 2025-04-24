package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

public enum SeedTypes {
    JazzSeeds(Seasons.Spring),
    CarrotSeeds(Seasons.Spring),
    CauliflowerSeeds(Seasons.Spring),
    CoffeeBean(Seasons.Spring),
    GarlicSeeds(Seasons.Spring),
    BeanStarter(Seasons.Spring),
    KaleSeeds(Seasons.Spring),
    ParsnipSeeds(Seasons.Spring),
    PotatoSeeds(Seasons.Spring),
    RhubarbSeeds(Seasons.Spring),
    StrawberrySeeds(Seasons.Spring),
    TulipBulb(Seasons.Spring),
    RiceShoot(Seasons.Spring),
    BlueberrySeeds(Seasons.Summer),
    CornSeeds(Seasons.Summer),
    HopsStarter(Seasons.Summer),
    PepperSeeds(Seasons.Summer),
    MelonSeeds(Seasons.Summer),
    PoppySeeds(Seasons.Summer),
    RadishSeeds(Seasons.Summer),
    RedCabbageSeeds(Seasons.Summer),
    StarfruitSeeds(Seasons.Summer),
    SpangleSeeds(Seasons.Summer),
    SummerSquashSeeds(Seasons.Summer),
    SunflowerSeeds(Seasons.Summer),
    TomatoSeeds(Seasons.Summer),
    WheatSeeds(Seasons.Summer),
    AmaranthSeeds(Seasons.Fall),
    ArtichokeSeeds(Seasons.Fall),
    BeetSeeds(Seasons.Fall),
    BokChoySeeds(Seasons.Fall),
    BroccoliSeeds(Seasons.Fall),
    CranberrySeeds(Seasons.Fall),
    EggplantSeeds(Seasons.Fall),
    FairySeeds(Seasons.Fall),
    GrapeStarter(Seasons.Fall),
    PumpkinSeeds(Seasons.Fall),
    YamSeeds(Seasons.Fall),
    RareSeed(Seasons.Fall),
    PowdermelonSeeds(Seasons.Fall),
    AncientSeeds(Seasons.Winter),
    MixedSeeds(Seasons.Winter);
    private Seasons season;
    SeedTypes(Seasons season) {
        this.season = season;
    }
}
