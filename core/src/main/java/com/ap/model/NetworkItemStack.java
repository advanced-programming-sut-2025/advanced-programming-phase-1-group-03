package com.ap.model;

import com.ap.asset.AtlasAsset;

public class NetworkItemStack {
    public String name;
    public int amount;
    public AtlasAsset atlasAsset;
    public String atlasKey;

    public NetworkItemStack() {
    }

    public NetworkItemStack(String name, int amount, AtlasAsset atlasAsset, String atlasKey) {
        this.name = name;
        this.amount = amount;
        this.atlasAsset = atlasAsset;
        this.atlasKey = atlasKey;
    }
}
