package phi.ap.model.enums;

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
}
