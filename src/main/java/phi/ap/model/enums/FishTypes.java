package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

public enum FishTypes {
    Salmon("Salmon", 75, Seasons.Fall, false),
    Sardine("Sardine", 40, Seasons.Fall, false),
    Shad("Shad", 60, Seasons.Fall, false),
    BlueDiscus("Blue Discus", 120, Seasons.Fall, false),
    MidnightCarp("Midnight Carp", 150, Seasons.Winter, false),
    Squid("Squid", 80, Seasons.Winter, false),
    Tuna("Tuna", 100, Seasons.Winter, false),
    Perch("Perch", 55, Seasons.Winter, false),
    Flounder("Flounder", 100, Seasons.Spring, false),
    Lionfish("Lionfish", 100, Seasons.Spring, false),
    Herring("Herring", 30, Seasons.Spring, false),
    Ghostfish("Ghostfish", 45, Seasons.Spring, false),
    Tilapia("Tilapia", 75, Seasons.Spring, false),
    Dorado("Dorado", 100, Seasons.Spring, false),
    Legend("Legend", 5000, Seasons.Spring, true),
    Glacierfish("Glacierfish", 1000, Seasons.Winter, true),
    Angler("Angler", 900, Seasons.Fall, true),
    Crimsonfish("Crimsonfish", 1500, Seasons.Summer, true);
    private String name;
    private int sellPrice;
    private Seasons season;
    private boolean fishingAbilityMustBeMax;
    FishTypes(String name, int price, Seasons season, boolean fishingAbilityMustBeMax) {
        this.name = name;
        this.sellPrice = price;
        this.season = season;
        this.fishingAbilityMustBeMax = fishingAbilityMustBeMax;
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

    public boolean isFishingAbilityMustBeMax() {
        return fishingAbilityMustBeMax;
    }
    public static FishTypes find(String name) {
        name = name.toLowerCase().replaceAll("\\s+", "");
        for (FishTypes value : values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }
}