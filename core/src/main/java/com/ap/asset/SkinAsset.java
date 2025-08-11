package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public enum SkinAsset implements Asset<Skin> {
    Default("skin.json")
    ;

    private final AssetDescriptor<Skin> descriptor;

    SkinAsset(String name) {
        descriptor = new AssetDescriptor<>("skins/" + name, Skin.class);
    }

    @Override
    public AssetDescriptor<Skin> getAssetDescriptor() {
        return descriptor;
    }
}
