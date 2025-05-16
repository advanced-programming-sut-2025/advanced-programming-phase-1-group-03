package phi.ap.model.enums;

import phi.ap.model.enums.npcStuff.NPCTypes;

public enum StoreTypes {
    Blacksmith("Blacksmith", "Clint", 9, 16, TileType.BlackSmithStore),
    MarnieRanch("MarnieRanch", "Marnie", 9, 16, TileType.MarnieSmithStore),
    TheStarDropSaloon("TheStarDropSaloon", "Gus", 12, 24, TileType.StarDropSaloon),
    CarpenterShop("CarpenterShop", "Robin", 9, 20, TileType.CarpenterShop),
    JojaMart("JojaMart", "Morris", 9, 23, TileType.JojaMart),
    PierreGeneralStore("PierreGeneralStore", "Pierre", 9, 17, TileType.PierreGeneralStore),
    FishShop("FishShop", "Willy", 9, 17, TileType.FishShop);

    private String storeManagerName;
    private int openingTime;
    private int closingTime;
    private TileType tileType;
    private String name;
    StoreTypes(String name, String storeManagerName, int openingTime, int closingTime, TileType tileType) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.name = name;
        this.tileType = tileType;
        this.storeManagerName = storeManagerName;
    }

    public NPCTypes getStoreManagerName() {
        return NPCTypes.findByName(storeManagerName);
    }

    public int getOpeningTime() {
        return openingTime;
    }

    public int getClosingTime() {
        return closingTime;
    }

    public String getName() {
        return name;
    }

    public static StoreTypes getType(String name) {
        StoreTypes storeType;
        try {
            storeType = StoreTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return storeType;
    }

    public TileType getTileType() {
        return tileType;
    }
}
