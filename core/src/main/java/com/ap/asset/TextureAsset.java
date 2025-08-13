package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

public enum TextureAsset implements Asset<Texture> {
    Rain("Rain.png"),
    GreenHouse("GreenHouse.png"),
    Grid("grid.png"),
    Wood("wood.png"),
    Grid2("grid2.png"),
    StoreBackground("StoreBackground.png");

    ;

    private final AssetDescriptor<Texture> descriptor;

    TextureAsset(String name) {
        descriptor = new AssetDescriptor<Texture>("graphics/" + name, Texture.class);
    }
    @Override
    public AssetDescriptor<Texture> getAssetDescriptor() {
        return descriptor;
    }
}
