package com.ap.notifiers;

import com.ap.model.NetworkItemStack;

public class InventoryNotifier {
    public NetworkItemStack[] items;
    public int storage;
    public boolean isRefrigerator;

    public InventoryNotifier() {
    }

    public InventoryNotifier(NetworkItemStack[] items, int storage, boolean isRefrigerator) {
        this.items = items;
        this.storage = storage;
        this.isRefrigerator = isRefrigerator;
    }
}
