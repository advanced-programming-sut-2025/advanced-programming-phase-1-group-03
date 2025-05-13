package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

public enum FishTypes {
    Salmon("Salmon", 75, Seasons.Fall),
    Sardine("Sardine", 40, Seasons.Fall),
    Shad("Shad", 60, Seasons.Fall),
    BlueDiscus("Blue Discus", 120, Seasons.Fall),
    MidnightCarp("Midnight Carp", 150, Seasons.Winter),
    Squid("Squid", 80, Seasons.Winter),
    Tuna("Tuna", 100, Seasons.Winter),
    Perch("Perch", 55, Seasons.Winter),
    Flounder("Flounder", 100, Seasons.Spring),
    Lionfish("Lionfish", 100, Seasons.Spring),
    Herring("Herring", 30, Seasons.Spring),
    Ghostfish("Ghostfish", 45, Seasons.Spring),
    Tilapia("Tilapia", 75, Seasons.Spring),
    Dorado("Dorado", 100, Seasons.Spring),
    Legend("Legend", 5000, Seasons.Spring),
    Glacierfish("Glacierfish", 1000, Seasons.Winter),
    Angler("Angler", 900, Seasons.Fall),
    Crimsonfish("Crimsonfish", 1500, Seasons.Summer);
    private String name;
    private int sellPrice;
    private Seasons season;
    FishTypes(String name, int price, Seasons season ) {
        this.name = name;
        this.sellPrice = price;
        this.season = season;
    }
    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return sellPrice;
    }

    public Seasons getSeason() {
        return season;
    }

    public static FishTypes getType(String name) {
        FishTypes fishTypes;
        try {
            fishTypes = FishTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return fishTypes;
    }
}