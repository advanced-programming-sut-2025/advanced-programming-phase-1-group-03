package phi.ap.model.enums;

import phi.ap.model.items.Item;

import java.util.ArrayList;

public enum StoreTypes {
    Blacksmith,
    MarnieRanch,
    TheStarDropSaloon,
    CarpenterShop,
    JojaMart,
    PierreGeneralStore,
    FishShop;
    private NPCTypes storeManager;
    private int openingTime;
    private int closingTime;
    private ArrayList<Item> products;
}
