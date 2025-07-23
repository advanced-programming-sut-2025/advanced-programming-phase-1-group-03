package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

public enum SoundAsset implements Asset<Sound> {
    HoverButton("hover-sound.wav"),
    Beep("coin.wav"),
    HoeHit("hoeHit.wav")
    ;

    private final AssetDescriptor<Sound> descriptor;

    SoundAsset(String name) {
        descriptor = new AssetDescriptor<>("audio/" + name, Sound.class);
    }
    @Override
    public AssetDescriptor<Sound> getAssetDescriptor() {
        return descriptor;
    }
}
