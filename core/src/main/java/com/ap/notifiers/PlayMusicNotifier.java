package com.ap.notifiers;

import com.ap.asset.MusicAsset;

public class PlayMusicNotifier {
    public boolean looping;
    public float volume;
    public MusicAsset musicAsset;

    public PlayMusicNotifier() {
    }

    public PlayMusicNotifier(boolean looping, float volume, MusicAsset musicAsset) {
        this.looping = looping;
        this.volume = volume;
        this.musicAsset = musicAsset;
    }
}
