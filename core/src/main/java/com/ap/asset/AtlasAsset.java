package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum AtlasAsset implements Asset<TextureAtlas> {
    Player("player.atlas"),
    Avatars("avatars.atlas"),
    UI("UI.atlas"),
    Tools("Tools.atlas"),
    ;

    private final AssetDescriptor<TextureAtlas> descriptor;
    AtlasAsset(String name) {
        descriptor = new AssetDescriptor<>("graphics/" + name, TextureAtlas.class);
    }
    @Override
    public AssetDescriptor<TextureAtlas> getAssetDescriptor() {
        return descriptor;
    }
}
