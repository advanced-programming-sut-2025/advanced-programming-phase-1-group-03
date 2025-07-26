package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

public enum TilesetAsset implements Asset<Texture> {
    OutdoorSpring("spring_outdoorsTileSheet.png"),
    OutdoorSummer("summer_outdoorsTileSheet.png"),
    OutdoorFall("fall_outdoorsTileSheet.png"),
    OutdoorWinter("winter_outdoorsTileSheet.png"),


    Outdoor2Spring("spring_outdoorsTileSheet2.png"),
    Outdoor2Summer("summer_outdoorsTileSheet2.png"),
    Outdoor2Fall("fall_outdoorsTileSheet2.png"),
    Outdoor2Winter("winter_outdoorsTileSheet2.png"),

    ShadowSpring("spring_Shadows.png"),
    ShadowSummer("summer_Shadows.png"),
    ShadowFall("fall_Shadows.png"),
    ShadowWinter("winter_Shadows.png"),

    WaterfallSpring("spring_Waterfalls.png"),
    WaterfallSummer("summer_Waterfalls.png"),
    WaterfallFall("fall_Waterfalls.png"),
    WaterfallWinter("winter_Waterfalls.png"),
    ;

    private final AssetDescriptor<Texture> descriptor;

    TilesetAsset(String name) {
        descriptor = new AssetDescriptor<>("map/tileset/" + name, Texture.class);
    }

    @Override
    public AssetDescriptor<Texture> getAssetDescriptor() {
        return descriptor;
    }
}
