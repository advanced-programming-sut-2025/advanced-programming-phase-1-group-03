package com.ap.managers;

import com.ap.asset.AssetService;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.model.Weather;
import com.ap.ui.effects.Rain;
import com.ap.ui.effects.Snowfall;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;

/*
This class is used for showing effects of each weather
 */
public class WeatherEffects {
    private Rain rainActor;
    private Snowfall snowActor;
    private Stage stage;
    private AudioService audioService;
    private AssetService assetService;
    private Engine engine;

    public WeatherEffects(AssetService assetService, Engine engine, Stage stage, AudioService audioService) {
        this.assetService = assetService;
        this.audioService = audioService;
        this.stage = stage;
    }

    public void onWeatherChanged(Weather weather) {
        removeActors();
        switch (weather) {
            case Sunny -> sunnySetup();
            case Rain -> rainSetup();
            case Storm -> stormSetup();
            case Snow -> snowSetup();
        }
    }


    private void snowSetup() {
        stage.addActor(snowActor);
    }

    private void stormSetup() {
    }

    public void rainSetup() {
        stage.addActor(rainActor);
        audioService.playMusicMeanwhile(MusicAsset.Rain);
    }

    public void sunnySetup() {

    }

    public void removeActors() {
        stage.getActors().removeValue(rainActor, true);
        stage.getActors().removeValue(snowActor, true);
        rainActor = new Rain(assetService);
        snowActor = new Snowfall(assetService, engine);
        audioService.stopMusic(MusicAsset.Rain);
    }
}
