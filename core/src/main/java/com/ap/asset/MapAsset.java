package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;

public enum MapAsset implements Asset<TiledMap>{
    Farm1("Farm1.tmx"),
    Forest("Forest.tmx"),
    House("House.tmx"),
    Farm2("Farm2.tmx"),
    Greenhouse("Greenhouse.tmx"),
    Town("Town.tmx"),
    Mine("Mine.tmx"),
    StardropSaloon("StardropSaloon.tmx");
    private final AssetDescriptor<TiledMap> descriptor;

    MapAsset(String name) {
        descriptor = new AssetDescriptor<>("map/" + name, TiledMap.class);
    }
    @Override
    public AssetDescriptor<TiledMap> getAssetDescriptor() {
        return descriptor;
    }
}
