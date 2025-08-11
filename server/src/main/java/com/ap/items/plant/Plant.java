package com.ap.items.plant;

import com.ap.asset.AtlasAsset;
import com.ap.items.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public abstract class Plant extends Item {
    public Plant(String name, int maxStackSize, AtlasAsset atlasAsset, String atlasKey, int sellPrice) {
        super(name, maxStackSize, atlasAsset, atlasKey, sellPrice);
    }
    public abstract List<Item> produceItems();
}
