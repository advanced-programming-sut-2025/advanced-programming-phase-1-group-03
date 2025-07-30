package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

public enum SoundAsset implements Asset<Sound> {
    HoverButton("hover-sound.wav"),
    Beep("coin.wav"),
    HoeHit("hoeHit.wav"),
    Scythe("cut.wav"),
    Axe("axe.wav"),
    Pickaxe("pickaxe.wav"),
    Walk("walk.wav"),
    Gift("gift.wav"),
    Watering("watering.wav"),
    WateringCanNo("wateringCanNo.wav"),
    CrowVoice("crow_1.wav"),
    BirdFlying("bird_flying.wav"),
    Thunder("thunder.mp3"),
    Mineral("mineral.wav"),
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
