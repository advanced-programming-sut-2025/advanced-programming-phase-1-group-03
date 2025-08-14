package com.ap.requests;

public class WorldClickRequest {
    public float worldY;
    public float worldX;
    public int button;
    public String itemName;
    public int itemAmount;

    public WorldClickRequest(float worldX, float worldY, int button, String itemName, int itemAmount) {
        this.worldY = worldY;
        this.worldX = worldX;
        this.button = button;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }

    public WorldClickRequest() {
    }
}
