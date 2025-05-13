package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.enums.Time.Seasons;

public enum ForagingTreeTypes {
    Acorns,
    MapleSeeds,
    PineCones,
    MahoganySeeds,
    MushroomTreeSeeds;

    public static ForagingTreeTypes find(String name) {
        name = name.toLowerCase();
        for (ForagingTreeTypes value : values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public TreeTypes getTreeType() {
        SeedTypes seed = SeedTypes.find(this.toString());
        return TreeTypes.findBySeed(seed);
    }

    public static ForagingTreeTypes getRandom(Seasons season) {
        int ind = App.getInstance().getRandomNumber(0, ForagingTreeTypes.values().length - 1);
        return ForagingTreeTypes.values()[ind];
        //all Seasons are special
    }

    public ForagingTreeTypes getType(String name) {
        ForagingTreeTypes foragingTreeTypes;
        try {
            foragingTreeTypes = ForagingTreeTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return foragingTreeTypes;
    }
}
