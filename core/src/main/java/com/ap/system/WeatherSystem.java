package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.model.Season;
import com.ap.model.Weather;
import com.ap.ui.effects.Rain;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class WeatherSystem extends EntitySystem {
    private Weather currentWeather = null;
    private final Clock clock;
    private final TimeSystem timeSystem;
    private final Stage stage;

    private final Rain rainActor;

    public WeatherSystem(final Engine engine,
                         final Clock clock,
                         AssetService assetService,
                         Stage stage) {

        timeSystem = engine.getSystem(TimeSystem.class);
        this.clock = clock;

        this.stage = stage;
        rainActor = new Rain(assetService);
        setWeatherRandomly();
        clock.setWeather(getCurrentWeather());

    }

    /**
     * This method set the weather to one of possible weathers randomly
     */
    public void setWeatherRandomly() {
        Season season = timeSystem.getSeason();
        int length = season.getPossibleWeathers().size();
        currentWeather = season.getPossibleWeathers().get(new Random().nextInt(length));
        switch (currentWeather) {
            case Sunny -> sunnySetup();
            case Rain -> rainSetup();
            case Storm -> stormSetup();
            case Snow -> snowSetup();
        }
    }

    private void snowSetup() {
    }

    private void stormSetup() {
    }

    public void rainSetup() {
        stage.addActor(rainActor);
    }
    public void sunnySetup() {
        stage.getActors().removeValue(rainActor, true);
    }

    @Override
    public void update(float deltaTime) {

    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }
}
