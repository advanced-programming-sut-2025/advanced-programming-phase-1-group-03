package com.ap.notifiers;

import com.ap.model.NetworkItemStack;

public class InventoryNotifier {
    public NetworkItemStack[] items;
    public int storage;

    public InventoryNotifier() {
    }

    public InventoryNotifier(NetworkItemStack[] items, int storage) {
        this.items = items;
        this.storage = storage;
    }
}
