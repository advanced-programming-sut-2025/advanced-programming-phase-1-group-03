package com.ap.notifiers;

import com.ap.asset.SoundAsset;

public class PlaySoundNotifier {
    public float volume;
    public SoundAsset soundAsset;

    public PlaySoundNotifier() {
    }

    public PlaySoundNotifier(float volume, SoundAsset soundAsset) {
        this.volume = volume;
        this.soundAsset = soundAsset;
    }
}
