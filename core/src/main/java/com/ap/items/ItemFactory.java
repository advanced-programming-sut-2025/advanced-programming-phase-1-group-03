package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.items.plant.Tree;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemFactory {
    public static ItemFactory instance = new ItemFactory();

    private AssetService assetService;

    public Item CreateGrass() {
        return new Item(ItemNames.Grass.name());
    }

    public Item CreateFiber() {
        TextureRegion icon = assetService.get(AtlasAsset.Environment).findRegion("grass/Fiber");
        return new Item(ItemNames.Fiber.name(), 64, icon);
    }
    public Item CreateStone() {
        TextureRegion icon = assetService.get(AtlasAsset.Environment).findRegions("stone/regular").get(0);
        return new Item(ItemNames.Stone.name(), 64, icon);
    }

    public Item CreateWood() {
        TextureRegion icon = assetService.get(AtlasAsset.Environment).findRegions("wood/regular").get(0);
        return new Item(ItemNames.Wood.name(), 64, icon);
    }

    public Item CreateTree() {
        return new Tree(ItemNames.Tree.name());
    }

    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }
}
