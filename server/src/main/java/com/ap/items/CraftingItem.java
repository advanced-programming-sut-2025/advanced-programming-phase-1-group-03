package com.ap.items;

import com.ap.asset.AtlasAsset;
import com.ap.model.Crafting;

public class CraftingItem extends Item{
    private Crafting crafting;
    public CraftingItem(String name, AtlasAsset atlasAsset, String atlasKey, Crafting crafting) {
        super(name, 0, atlasAsset, atlasKey, 0);
        this.crafting = crafting;
    }
}
