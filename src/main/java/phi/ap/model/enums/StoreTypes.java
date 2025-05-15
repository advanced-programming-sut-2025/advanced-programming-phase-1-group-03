package phi.ap.model.enums;

import phi.ap.model.enums.npcStuff.NPCTypes;
import phi.ap.model.items.Item;

import java.util.ArrayList;

public enum StoreTypes {
    Blacksmith("Blacksmith", NPCTypes.Clint, 9, 16, TileType.BlackSmithStore),
    MarnieRanch("MarnieRanch", NPCTypes.Marnie, 9, 16, TileType.MarnieSmithStore),
    TheStarDropSaloon("TheStarDropSaloon", NPCTypes.Gus, 12, 24, TileType.StarDropSaloon),
    CarpenterShop("CarpenterShop", NPCTypes.Robin, 9, 20, TileType.CarpenterShop),
    JojaMart("JojaMart", NPCTypes.Morris, 9, 23, TileType.JojaMart),
    PierreGeneralStore("PierreGeneralStore", NPCTypes.Pierre, 9, 17, TileType.PierreGeneralStore),
    FishShop("FishShop", NPCTypes.Willy, 9, 17, TileType.FishShop);
    private NPCTypes storeManager;


    private int openingTime;
    private int closingTime;
    private TileType tileType;
    private String name;
    StoreTypes(String name, NPCTypes npcTypes, int openingTime, int closingTime, TileType tileType) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.name = name;
        this.tileType = tileType;
        this.storeManager = npcTypes;
    }

    public NPCTypes getStoreManager() {
        return storeManager;
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
