package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;

public enum MapAsset implements Asset<TiledMap>{
    Farm1("Farm1.tmx", false),
    Forest("Forest.tmx", false),
    House("House.tmx", false),
    Farm2("Farm2.tmx", false),
    Greenhouse("Greenhouse.tmx", false),
    Town("Town.tmx", true),
    Mine("Mine.tmx", false),
    StardropSaloon("StardropSaloon.tmx", false),
    CarpenterShop("Carpenter.tmx", false),
    MarniesRanch("MarniesRanch.tmx", false),
    Barn("Barn.tmx", false),
    BigBarn("Barn2.tmx", false),
    DeluxeBarn("Barn3.tmx", false),
    Coop("Coop.tmx", false),
    BigCoop("Coop2.tmx", false),
    DeluxeCoop("Coop3.tmx", false)
    ;

    private final AssetDescriptor<TiledMap> descriptor;
    public final boolean isMapPublic;

    MapAsset(String name, boolean mapPublic) {
        descriptor = new AssetDescriptor<>("map/" + name, TiledMap.class);
        this.isMapPublic = mapPublic;
    }
    @Override
    public AssetDescriptor<TiledMap> getAssetDescriptor() {
        return descriptor;
    }
}
