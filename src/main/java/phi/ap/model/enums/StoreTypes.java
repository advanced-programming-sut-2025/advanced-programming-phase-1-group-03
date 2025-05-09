package phi.ap.model.enums;

import phi.ap.model.NPC;
import phi.ap.model.enums.StoreProducts.BlackSmithsProducts;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.Arrays;

public enum StoreTypes {
    Blacksmith(NPCTypes.Clint, 9, 16),
    MarnieRanch(NPCTypes.Marnie, 9, 16),
    TheStarDropSaloon(NPCTypes.Gus, 12, 24),
    CarpenterShop(NPCTypes.Robin, 9, 20),
    JojaMart(NPCTypes.Morris, 9, 23),
    PierreGeneralStore(NPCTypes.Pierre, 9, 17),
    FishShop(NPCTypes.Willy, 9, 17);
    private NPCTypes storeManager;
    private int openingTime;
    private int closingTime;
    private ArrayList<Item> products;
    StoreTypes(NPCTypes npcTypes, int openingTime, int closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.products = products;
    }
}
