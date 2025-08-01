package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum AtlasAsset implements Asset<TextureAtlas> {
    Player("player.atlas"),
    Avatars("avatars.atlas"),
    UI("UI.atlas"),
    Tools("Tools.atlas"),
    SeasonalObjects("SeasonalObjects.atlas"),
    Trees("OnMap/trees.atlas"),
    Shadows("shadow.atlas"),
    Environment("OnMap/environment.atlas"),
    Fish("Animals/Fish.atlas"),
    Snowfall("OnMap/snowfall.atlas"),
    Journal("Journal.atlas"),
    UIElements("UI_elements.atlas"),
    Toggles("Tabs/Toggles.atlas"),
    Crops("Plants/crops.atlas"),
    Crafting("Crafting.atlas"),
    Cooking("Cooking.atlas"),
    Character("Characters.atlas"),
    Crow("Animals/Crow.atlas"),
    Mineral("Mineral.atlas"),
    Foods("Foods/Foods.atlas");

    private final AssetDescriptor<TextureAtlas> descriptor;
    AtlasAsset(String name) {
        descriptor = new AssetDescriptor<>("graphics/" + name, TextureAtlas.class);
    }
    @Override
    public AssetDescriptor<TextureAtlas> getAssetDescriptor() {
        return descriptor;
    }
}
