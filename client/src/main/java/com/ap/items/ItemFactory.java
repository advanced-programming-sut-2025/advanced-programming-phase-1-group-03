package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemFactory {
    public static ItemFactory instance = new ItemFactory();

    private AssetService assetService;

    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }
}
