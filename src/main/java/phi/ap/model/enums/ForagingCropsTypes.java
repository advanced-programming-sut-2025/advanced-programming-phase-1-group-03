package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

public enum ForagingCropsTypes {
    COMMON_MUSHROOM("c", Colors.fg(52), "", "Common Mushroom", Seasons.Special, 38, 1),
    DAFFODIL("c", Colors.fg(52), "", "Daffodil", Seasons.Spring, 0, 1),
    DANDELION("c", Colors.fg(52), "", "Dandelion", Seasons.Spring, 25, 1),
    LEEK("c", Colors.fg(52), "", "Leek", Seasons.Spring, 40, 1),
    MOREL("c", Colors.fg(52), "", "Morel", Seasons.Spring, 20, 1),
    SALMONBERRY("c", Colors.fg(52), "", "Salmonberry", Seasons.Spring, 25, 1),
    SPRING_ONION("c", Colors.fg(52), "", "Spring Onion", Seasons.Spring, 13, 1),
    WILD_HORSERADISH("c", Colors.fg(52), "", "Wild Horseradish", Seasons.Spring, 13, 1),
    FIDDLEHEAD_FERN("c", Colors.fg(52), "", "Fiddlehead Fern", Seasons.Summer, 25, 1),
    GRAPE("c", Colors.fg(52), "", "Grape", Seasons.Summer, 38, 1),
    RED_MUSHROOM("c", Colors.fg(52), "", "Red Mushroom", Seasons.Summer, -50, 1),
    SPICE_BERRY("c", Colors.fg(52), "", "Spice Berry", Seasons.Summer, 25, 1),
    SWEET_PEA("c", Colors.fg(52), "", "Sweet Pea", Seasons.Summer, 0, 1),
    BLACKBERRY("c", Colors.fg(52), "", "Blackberry", Seasons.Fall, 25, 1),
    CHANTERELLE("c", Colors.fg(52), "", "Chanterelle", Seasons.Fall, 75, 1),
    HAZELNUT("c", Colors.fg(52), "", "Hazelnut", Seasons.Fall, 30, 1),
    PURPLE_MUSHROOM("c", Colors.fg(52), "", "Purple Mushroom", Seasons.Fall, 38, 1),
    WILD_PLUM("c", Colors.fg(52), "", "Wild Plum", Seasons.Fall, 25, 1),
    CROCUS("c", Colors.fg(52), "", "Crocus", Seasons.Winter, 0, 1),
    CRYSTAL_FRUIT("c", Colors.fg(52), "", "Crystal Fruit", Seasons.Winter, 63, 1),
    HOLLY("c", Colors.fg(52), "", "Holly", Seasons.Winter, -37, 1),
    SNOW_YAM("c", Colors.fg(52), "", "Snow Yam", Seasons.Winter, 30, 1),
    WINTER_ROOT("c", Colors.fg(52), "", "Winter Root", Seasons.Winter, 25, 1);

private final String name;
    private final Seasons season;
    private final int energy;
    private Tile tile;
    private int stackSize;

    ForagingCropsTypes(String symbol,
                       String fgColor,
                       String bgColor,
                       String name,
                       Seasons season,
                       int energy,
                       int stackSize) {
        this.name = name;
        this.season = season;
        this.energy = energy;
        this.tile = new Tile(symbol, fgColor, bgColor);
        this.stackSize = stackSize;
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
        return result;
    }

    public int getStackSize() {
        return stackSize;
    }
}
