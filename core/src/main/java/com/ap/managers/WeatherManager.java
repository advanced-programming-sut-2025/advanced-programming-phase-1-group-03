package com.ap.managers;

import com.ap.asset.AssetService;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.model.Weather;
import com.ap.system.WeatherSystem;
import com.ap.ui.effects.Rain;
import com.ap.ui.effects.Snowfall;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WeatherManager {
    private final Rain rainActor;
    private final Snowfall snowActor;
    private Stage stage;
    private AudioService audioService;

    public WeatherManager(AssetService assetService, Engine engine, Stage stage, AudioService audioService) {
        rainActor = new Rain(assetService);
        snowActor = new Snowfall(assetService, engine);
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

    private void removeActors() {
        stage.getActors().removeValue(rainActor, true);
        stage.getActors().removeValue(snowActor, true);
    }
}
