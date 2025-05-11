package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.Arrays;

public enum CropsTypes {
    BlueJazz("Blue Jazz", SeedTypes.JazzSeeds, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, 100000000, 50, new Eatable(45), 45, Seasons.Spring, false),
    CARROT("Carrot", SeedTypes.CarrotSeeds, new ArrayList<>(Arrays.asList(1, 1, 1)), 3, true, 100000000, 35, new Eatable(75), 75, Seasons.Spring, false),
    CAULIFLOWER("Cauliflower", SeedTypes.CauliflowerSeeds, new ArrayList<>(Arrays.asList(1, 2, 4, 4, 1)), 12, true, 100000000, 175, new Eatable(75), 75, Seasons.Spring, true),
    COFFEE_BEAN("Coffee Bean", SeedTypes.CoffeeBean, new ArrayList<>(Arrays.asList(1, 2, 2, 3, 2)), 10, false, 2, 15, null, 0, Seasons.Spring, false),
    GARLIC("Garlic", SeedTypes.GarlicSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, 100000000, 60, new Eatable(20), 20, Seasons.Spring, false),
    GREEN_BEAN("Green Bean", SeedTypes.BeanStarter, new ArrayList<>(Arrays.asList(1, 1, 1, 3, 4)), 10, false, 3, 40, new Eatable(25), 25, Seasons.Spring, false),
    KALE("Kale", SeedTypes.KaleSeeds, new ArrayList<>(Arrays.asList(1, 2, 2, 1)), 6, true, 100000000, 110, new Eatable(50), 50, Seasons.Spring, false),
    PARSNIP("Parsnip", SeedTypes.ParsnipSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, 100000000, 35, new Eatable(25), 25, Seasons.Spring, false),
    POTATO("Potato", SeedTypes.PotatoSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)), 6, true, 100000000, 80, new Eatable(25), 25, Seasons.Spring, false),
    RHUBARB("Rhubarb", SeedTypes.RhubarbSeeds, new ArrayList<>(Arrays.asList(2, 2, 2, 3, 4)), 13, true, 100000000, 220, null, 0, Seasons.Spring, false),
    STRAWBERRY("Strawberry", SeedTypes.StrawberrySeeds, new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2)), 8, false, 4, 120, new Eatable(50), 50, Seasons.Spring, false),
    TULIP("Tulip", SeedTypes.TulipBulb, new ArrayList<>(Arrays.asList(1, 1, 2, 2)), 6, true, 100000000, 30, new Eatable(45), 45, Seasons.Spring, false),
    UNMILLED_RICE("Unmilled Rice", SeedTypes.RiceShoot, new ArrayList<>(Arrays.asList(1, 2, 2, 3)), 8, true, 100000000, 30, new Eatable(3), 3, Seasons.Spring, false),
    BLUEBERRY("Blueberry", SeedTypes.BlueberrySeeds, new ArrayList<>(Arrays.asList(1, 3, 3, 4, 2)), 13, false, 4, 50, new Eatable(25), 25, Seasons.Spring, false),
    Corn("Corn", SeedTypes.CornSeeds, new ArrayList<>(Arrays.asList(2, 3, 3, 3, 3)), 14, false, 4, 50, new Eatable(25), 25, Seasons.Summer, false),
    Hops("Hops", SeedTypes.HopsStarter, new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4)), 11, false, 1, 25, new Eatable(45), 45, Seasons.Summer, false),
    HotPepper("Hot Pepper", SeedTypes.PepperSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 3, 1)), 5, false, 3, 40, new Eatable(13), 13, Seasons.Summer, false),
    melon("Melon", SeedTypes.MelonSeeds, new ArrayList<>(Arrays.asList(1, 2, 3, 3, 3)), 12, true, 100000000, 250, new Eatable(113), 113, Seasons.Summer, true),
    POPPY("Poppy", SeedTypes.PoppySeeds, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, 100000000, 140, new Eatable(45), 45, Seasons.Summer, false),
    RADISH("Radish", SeedTypes.RadishSeeds, new ArrayList<>(Arrays.asList(2, 1, 2, 1)), 6, true, 100000000, 90, new Eatable(45), 45, Seasons.Summer, false),
    RED_CABBAGE("Red Cabbage", SeedTypes.RedCabbageSeeds, new ArrayList<>(Arrays.asList(2, 1, 2, 2, 2)), 9, true, 100000000, 260, new Eatable(75), 75, Seasons.Summer, false),
    STARFRUIT("Starfruit", SeedTypes.StarfruitSeeds, new ArrayList<>(Arrays.asList(1, 2, 3, 3, 4)), 13, true, 100000000, 750, new Eatable(125), 125, Seasons.Summer, false),
    SUMMER_SPANGLE("Summer Spangle", SeedTypes.SpangleSeeds, new ArrayList<>(Arrays.asList(1, 2, 3, 1)), 8, true, 100000000, 90, new Eatable(45), 45, Seasons.Summer, false),
    SUMMER_SQUASH("Summer Squash", SeedTypes.SummerSquashSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)), 6, false, 100000000, 45, new Eatable(63), 63, Seasons.Summer, false),
    SUNFLOWER("Sunflower", SeedTypes.SunflowerSeeds, new ArrayList<>(Arrays.asList(1, 2, 3, 2)), 8, true, 100000000, 80, new Eatable(45), 45, Seasons.Summer, false),
    TOMATO("Tomato", SeedTypes.TomatoSeeds, new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3)), 11, false, 4, 60, new Eatable(20), 20, Seasons.Summer, false),
    WHEAT("Wheat", SeedTypes.WheatSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, 100000000, 25, null, 0, Seasons.Summer, false),
    AMARANTH("Amaranth", SeedTypes.AmaranthSeeds,new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, 100000000, 150, new Eatable(50), 50, Seasons.Fall, false),
    ARTICHOKE("Artichoke", SeedTypes.ArtichokeSeeds, new ArrayList<>(Arrays.asList(2, 2, 1, 2, 1)), 8, true, 100000000, 160, new Eatable(30), 30, Seasons.Fall, false),
    BEET("Beet", SeedTypes.BeetSeeds, new ArrayList<>(Arrays.asList(1, 1, 2, 2)), 6, true, 100000000, 100, new Eatable(30),30, Seasons.Fall, false),
    BOKCHOY("Bok Choy", SeedTypes.BokChoySeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, 100000000, 80, new Eatable(25), 25,  Seasons.Fall, false),
    BROCCOLI("Broccoli", SeedTypes.BroccoliSeeds, new ArrayList<>(Arrays.asList(2, 2, 2, 2)), 8, false, 4, 70, new Eatable(63), 63, Seasons.Fall, false),
    CRANBERRIES("Cranberries", SeedTypes.CranberrySeeds, new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2)), 7, false, 5, 75, new Eatable(38), 38, Seasons.Fall, false),
    EGGPLANT("Eggplant", SeedTypes.EggplantSeeds, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 5, false, 5, 60, new Eatable(20), 20, Seasons.Fall, false),
    FAIRY_ROSE("Fairy Rose", SeedTypes.FairySeeds, new ArrayList<>(Arrays.asList(1, 4, 4, 3)), 12, true, 100000000, 290, new Eatable(45), 45, Seasons.Fall, false),
    GRAPE("Grape", SeedTypes.GrapeStarter, new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3)), 10, false, 3, 80, new Eatable(38), 38, Seasons.Fall, false),
    PUMPKIN("Pumpkin", SeedTypes.PumpkinSeeds, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 3)), 13, true, 100000000, 320, null,0,  Seasons.Fall, true),
    YAM("Yam", SeedTypes.YamSeeds, new ArrayList<>(Arrays.asList(1, 3, 3, 3)), 10, true, 100000000, 160, new Eatable(45), 45,  Seasons.Fall, false),
    SWEET_GEM_BERRY("Sweet Gem Berry", SeedTypes.RareSeed, new ArrayList<>(Arrays.asList(2, 4, 6, 6, 6)), 24, true, 100000000, 3000, null, 0, Seasons.Fall, false),
    POWDERMELON("Powdermelon", SeedTypes.PowdermelonSeeds, new ArrayList<>(Arrays.asList(1, 2, 1, 2, 1)), 7, true, 100000000, 60, new Eatable(63), 63, Seasons.Fall, true),
    ANCIENT_FRUIT("Ancient Fruit", SeedTypes.AncientSeeds, new ArrayList<>(Arrays.asList(2, 7, 7, 7, 5)), 28, false, 7, 550, null, 0, Seasons.Fall, false);
    private final String name;
    private final SeedTypes source;
    private final ArrayList<Integer> stage;
    private final int harvestTime;
    private final boolean onTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final Eatable eatable;
    private final int energy;
    private final Seasons season;
    private final boolean canBecomeGiant;

    CropsTypes(String name, SeedTypes source, ArrayList<Integer> stage, int harvestTime, boolean onTime, int regrowthTime, int baseSellPrice, Eatable eatable, int energy, Seasons season, boolean canBecomeGiant) {
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

    public String getName() {
        return name;
    }
    public SeedTypes getSeed() {
        return source;
    }
    public ArrayList<Integer> getStage() {
        return stage;
    }
    public int getHarvestTime() {
        return harvestTime;
    }
    public Boolean getOnTime() {
        return onTime;
    }
    public int getRegrowthTime() {
        return regrowthTime;
    }
    public int getBaseSellPrice() {
        return baseSellPrice;
    }
    public Eatable getEatable() {
        return eatable;
    }
    public int getEnergy() {
        return energy;
    }
    public Seasons getSeason() {
        return season;
    }
    public Boolean getCanBecomeGiant() {
        return canBecomeGiant;
    }
}
