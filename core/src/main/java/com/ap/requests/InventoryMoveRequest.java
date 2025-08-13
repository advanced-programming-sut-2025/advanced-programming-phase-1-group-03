package com.ap.requests;

import com.ap.model.NetworkItemStack;

public class InventoryMoveRequest {
    public NetworkItemStack[] items;
    public int storage;
    public boolean toRefrigerator;

    public InventoryMoveRequest() {
    }

    public InventoryMoveRequest(NetworkItemStack[] items, int storage, boolean isRefrigerator) {
        this.items = items;
        this.storage = storage;
        this.toRefrigerator = isRefrigerator;
    }
}
