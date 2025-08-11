package com.ap.audio;

import com.ap.asset.MusicAsset;
import com.ap.asset.SoundAsset;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.PlayMusicNotifier;
import com.ap.notifiers.PlaySoundNotifier;
import com.badlogic.gdx.math.MathUtils;

public class AudioService {
    private MusicAsset currentMusicAsset;
    private float musicVolume = 0.3f;
    private float soundVolume = 0.3f;

    private final ServerPlayer player;

    public AudioService(ServerPlayer player) {
        this.player = player;
    }

    public void playMusic(MusicAsset musicAsset) {
        // We are playing this music now!
        if(currentMusicAsset == musicAsset) {
            return;
        }

        currentMusicAsset = musicAsset;
        player.connection.sendTCP(new PlayMusicNotifier(true, musicVolume, currentMusicAsset));
    }


    public void playSound(SoundAsset soundAsset) {
        player.connection.sendTCP(new PlaySoundNotifier(soundVolume, soundAsset));
    }
    public void playSound(SoundAsset soundAsset, float volume) {
        player.connection.sendTCP(new PlaySoundNotifier(volume, soundAsset));
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = MathUtils.clamp(soundVolume, 0, 1);
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = MathUtils.clamp(musicVolume, 0, 1);
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }
}
