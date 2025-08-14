package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.model.ServerPlayer;

public class Refrigerator {
    private Inventory inventory;

    public Refrigerator(ServerPlayer serverPlayer) {
        inventory = new Inventory(serverPlayer);
    }

    public void addItem(Item item, int amount) {
        inventory.addItem(item, amount, true);
    }

    public void removeItem(Item item, int amount) {
        inventory.removeItem(item, amount, true);
    }

    public Inventory getInventory(){
        return inventory;
    }
}
