package com.ap.requests;

public class RemoveItemInventoryRequest {
    public String itemName;
    public int amount;

    public RemoveItemInventoryRequest() {
    }

    public RemoveItemInventoryRequest(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
    }
}
