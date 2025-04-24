package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

public enum ForagingCropsTypes {
    COMMON_MUSHROOM("Common Mushroom", Seasons.Special, 38),
    DAFFODIL("Daffodil", Seasons.Spring, 0),
    DANDELION("Dandelion", Seasons.Spring, 25),
    LEEK("Leek", Seasons.Spring, 40),
    MOREL("Morel", Seasons.Spring, 20),
    SALMONBERRY("Salmonberry", Seasons.Spring, 25),
    SPRING_ONION("Spring Onion", Seasons.Spring, 13),
    WILD_HORSERADISH("Wild Horseradish", Seasons.Spring, 13),
    FIDDLEHEAD_FERN("Fiddlehead Fern", Seasons.Summer, 25),
    GRAPE("Grape", Seasons.Summer, 38),
    RED_MUSHROOM("Red Mushroom", Seasons.Summer, -50),
    SPICE_BERRY("Spice Berry", Seasons.Summer, 25),
    SWEET_PEA("Sweet Pea", Seasons.Summer, 0),
    BLACKBERRY("Blackberry", Seasons.Fall, 25),
    CHANTERELLE("Chanterelle", Seasons.Fall, 75),
    HAZELNUT("Hazelnut", Seasons.Fall, 30),
    PURPLE_MUSHROOM("Purple Mushroom", Seasons.Fall, 38),
    WILD_PLUM("Wild Plum", Seasons.Fall, 25),
    CROCUS("Crocus", Seasons.Winter, 0),
    CRYSTAL_FRUIT("Crystal Fruit", Seasons.Winter, 63),
    HOLLY("Holly", Seasons.Winter, -37),
    SNOW_YAM("Snow Yam", Seasons.Winter, 30),
    WINTER_ROOT("Winter Root", Seasons.Winter, 25);

    private final String name;
    private final Seasons season;
    private final int energy;

    ForagingCropsTypes(String name, Seasons season, int energy) {
        this.name = name;
        this.season = season;
        this.energy = energy;
    }
}
