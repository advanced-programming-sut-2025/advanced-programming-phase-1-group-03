package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

public enum MusicAsset implements Asset<Music> {
    Default("Overture.mp3"),
    GameMusicDefault("Game1.mp3")
    ;

    private final AssetDescriptor<Music> descriptor;

    MusicAsset(String name) {
        descriptor = new AssetDescriptor<Music>("audio/" + name, Music.class);
    }
    @Override
    public AssetDescriptor<Music> getAssetDescriptor() {
        return descriptor;
    }
}
