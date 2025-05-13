package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.List;

public enum ForagingCropsTypes {
    CommonMushroom("c", Colors.fg(52), "", "Common Mushroom", Seasons.Special, 38, 1, 40),
    Daffodil("c", Colors.fg(52), "", "Daffodil", Seasons.Spring, 0, 1, 30),
    Dandelion("c", Colors.fg(52), "", "Dandelion", Seasons.Spring, 25, 1, 40),
    Leek("c", Colors.fg(52), "", "Leek", Seasons.Spring, 40, 1, 60),
    Morel("c", Colors.fg(52), "", "Morel", Seasons.Spring, 20, 1, 150),
    Salmonberry("c", Colors.fg(52), "", "Salmonberry", Seasons.Spring, 25, 1, 5),
    SpringOnion("c", Colors.fg(52), "", "Spring Onion", Seasons.Spring, 13, 1, 8),
    WildHorseradish("c", Colors.fg(52), "", "Wild Horseradish", Seasons.Spring, 13, 1, 50),
    FiddleheadFern("c", Colors.fg(52), "", "Fiddlehead Fern", Seasons.Summer, 25, 1, 90),
    Grape("c", Colors.fg(52), "", "Grape", Seasons.Summer, 38, 1, 80),
    RedMushroom("c", Colors.fg(52), "", "Red Mushroom", Seasons.Summer, -50, 1, 75),
    SpiceBerry("c", Colors.fg(52), "", "Spice Berry", Seasons.Summer, 25, 1, 80),
    SweetPea("c", Colors.fg(52), "", "Sweet Pea", Seasons.Summer, 0, 1, 50),
    BlackBerry("c", Colors.fg(52), "", "Blackberry", Seasons.Fall, 25, 1, 25),
    Chanterelle("c", Colors.fg(52), "", "Chanterelle", Seasons.Fall, 75, 1, 160),
    Hazelnut("c", Colors.fg(52), "", "Hazelnut", Seasons.Fall, 30, 1, 40),
    PurpleMushroom("c", Colors.fg(52), "", "Purple Mushroom", Seasons.Fall, 38, 1, 90),
    WildPum("c", Colors.fg(52), "", "Wild Plum", Seasons.Fall, 25, 1, 80),
    Crocus("c", Colors.fg(52), "", "Crocus", Seasons.Winter, 0, 1, 60),
    CrystalFruit("c", Colors.fg(52), "", "Crystal Fruit", Seasons.Winter, 63, 1, 150),
    Holly("c", Colors.fg(52), "", "Holly", Seasons.Winter, -37, 1, 80),
    SnowYam("c", Colors.fg(52), "", "Snow Yam", Seasons.Winter, 30, 1, 100),
    WinterRoot("c", Colors.fg(52), "", "Winter Root", Seasons.Winter, 25, 1, 70);

private final String name;
    private final Seasons season;
    private final int energy;
    private Tile tile;
    private int stackSize;
    private int baseSellPrice;

    ForagingCropsTypes(String symbol,
                       String fgColor,
                       String bgColor,
                       String name,
                       Seasons season,
                       int energy,
                       int stackSize, int baseSellPrice) {
        this.name = name;
        this.season = season;
        this.energy = energy;
        this.tile = new Tile(symbol, fgColor, bgColor);
        this.stackSize = stackSize;
        this.baseSellPrice = baseSellPrice;
    }

    public Tile getTile() {
        return tile;
    }

    public String getName() {
        return name;
    }

    public Seasons getSeason() {
        return season;
    }

    public int getEnergy() {
        return energy;
    }

    public static ForagingCropsTypes getRandomFromSeason(Seasons season) {
        ForagingCropsTypes result = null;
        do {
            int ind = App.getInstance().getRandomNumber(0, ForagingCropsTypes.values().length - 1);
            result = ForagingCropsTypes.values()[ind];
        } while (result.season != season && result.season != Seasons.Special);
        return CommonMushroom;
    }

    public int getStackSize() {
        return stackSize;
    }

    public static ForagingCropsTypes find(String name) {
        name = name.toLowerCase();
        for (ForagingCropsTypes value : values()) {
            if (value.getName().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public ArrayList<Seasons> getSeasonList() {
        if (season != Seasons.Special) return new ArrayList<>(List.of(season));
        return new ArrayList<>(List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));
    }

    public static ForagingCropsTypes getType(String name) {
        ForagingCropsTypes foragingCropsType;
        try {
            foragingCropsType = ForagingCropsTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return foragingCropsType;
    }
}
