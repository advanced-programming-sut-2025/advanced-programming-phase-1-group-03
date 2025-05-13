package phi.ap.model.enums;

import phi.ap.model.NPC;
import phi.ap.model.enums.StoreProducts.BlackSmithsProducts;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.Arrays;

public enum StoreTypes {
    Blacksmith("Blacksmith", NPCTypes.Clint, 9, 16),
    MarnieRanch("MarnieRanch", NPCTypes.Marnie, 9, 16),
    TheStarDropSaloon("TheStarDropSaloon", NPCTypes.Gus, 12, 24),
    CarpenterShop("CarpenterShop", NPCTypes.Robin, 9, 20),
    JojaMart("JojaMart", NPCTypes.Morris, 9, 23),
    PierreGeneralStore("PierreGeneralStore", NPCTypes.Pierre, 9, 17),
    FishShop("FishShop", NPCTypes.Willy, 9, 17);
    private NPCTypes storeManager;


    private int openingTime;
    private int closingTime;
    private ArrayList<Item> products;
    private String name;
    StoreTypes(String name, NPCTypes npcTypes, int openingTime, int closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.products = products;
        this.name = name;
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

    public ArrayList<Item> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    public StoreTypes getType(String name) {
        StoreTypes storeType;
        try {
            storeType = StoreTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return storeType;
    }
}
