package com.ap.audio;

import com.ap.asset.AssetService;
import com.ap.asset.MusicAsset;
import com.ap.asset.SoundAsset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.Map;

public class AudioService {
    private final AssetService assetManager;
    private Music currentMusic;
    private MusicAsset currentMusicAsset;
    private float musicVolume = 0.3f;
    private float soundVolume = 0.3f;

    private Map<MusicAsset, Music> musicMap = new HashMap<>();
    private Map<SoundAsset, Sound> soundCache =new HashMap<>();
    public AudioService(AssetService assetManager) {
        this.assetManager = assetManager;
    }

    public void playMusic(MusicAsset musicAsset) {
        Gdx.app.postRunnable(() -> {
            // We are playing this music now!
            if(currentMusicAsset == musicAsset) {
                return;
            }

            if(currentMusic != null) {
                currentMusic.stop();
                assetManager.unload(currentMusicAsset);
            }

            currentMusicAsset = musicAsset;
            currentMusic = assetManager.get(currentMusicAsset);
            currentMusic.setLooping(true);
            currentMusic.setVolume(musicVolume);
            currentMusic.play();
        });
    }
    public void playMusic(MusicAsset musicAsset, boolean looping, float volume) {
        Gdx.app.postRunnable(() -> {
            // We are playing this music now!
            if(currentMusicAsset == musicAsset) {
                return;
            }

            if(currentMusic != null) {
                currentMusic.stop();
                assetManager.unload(currentMusicAsset);
            }

            currentMusicAsset = musicAsset;
            currentMusic = assetManager.get(currentMusicAsset);
            currentMusic.setLooping(looping);
            currentMusic.setVolume(volume);
            currentMusic.play();
        });
    }

    public void playMusicMeanwhile(MusicAsset musicAsset) {
        Gdx.app.postRunnable(() -> {
            if (currentMusic != null) {
                currentMusic.setVolume(MathUtils.clamp(musicVolume - 0.1f, 0, 1));
            }
            if (musicMap.containsKey(musicAsset)) {
                return;
            }
            Music music = assetManager.get(musicAsset);
            musicMap.put(musicAsset, music);
            music.setLooping(true);
            music.setVolume(musicVolume);
            music.play();
        });
    }

    public void stopMusic(MusicAsset musicAsset) {
        if(!musicMap.containsKey(musicAsset)) {
            return;
        }
        Music music = musicMap.get(musicAsset);
        music.stop();
        assetManager.unload(musicAsset);
        musicMap.remove(musicAsset);
    }

    public void playSound(SoundAsset soundAsset) {
        Gdx.app.postRunnable(() -> {
            assetManager.get(soundAsset).play(soundVolume);
        });
    }


    public void playSound(SoundAsset soundAsset, float volume) {
        Gdx.app.postRunnable(() -> {
           assetManager.get(soundAsset).play(volume);
        });
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = MathUtils.clamp(soundVolume, 0, 1);
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = MathUtils.clamp(musicVolume, 0, 1);
        if(currentMusic != null) {
            currentMusic.setVolume(musicVolume);
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }
}
