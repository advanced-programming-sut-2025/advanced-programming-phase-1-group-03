package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.items.plant.Tree;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemFactory {
    public static Item CreateGrass() {
        return new Item(ItemNames.Grass.name());
    }
    public static Item CreateFiber(AssetService assetService) {
        TextureRegion icon = assetService.get(AtlasAsset.Environment).findRegion("grass/Fiber");
        return new Item(ItemNames.Fiber.name(), 64, icon);
    }
    public static Item CreateStone(AssetService assetService) {
        TextureRegion icon = assetService.get(AtlasAsset.Environment).findRegions("stone/regular").get(0);
        return new Item(ItemNames.Stone.name(), 64, icon);
    }

    public static Item CreateWood(AssetService assetService) {
        TextureRegion icon = assetService.get(AtlasAsset.Environment).findRegions("wood/regular").get(0);
        return new Item(ItemNames.Wood.name(), 64, icon);
    }

    public static Item CreateTree() {
        return new Tree(ItemNames.Tree.name());
    }

}
