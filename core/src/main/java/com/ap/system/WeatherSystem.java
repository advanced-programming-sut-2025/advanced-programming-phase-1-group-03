package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.model.Season;
import com.ap.model.Weather;
import com.ap.system.universal.TimeSystem;
import com.ap.ui.effects.Rain;
import com.ap.ui.effects.Snowfall;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;
import java.util.function.Consumer;

public class WeatherSystem extends EntitySystem {
    private Weather currentWeather = null;
    private final Clock clock;
    private final TimeSystem timeSystem;
    private final Stage stage;
    private final AudioService audioService;
    private Consumer<Weather> weatherConsumer;

    public WeatherSystem(final Clock clock,
                         AssetService assetService,
                         Stage stage,
                         AudioService audioService,
                         TimeSystem timeSystem) {

        this.timeSystem = timeSystem;
        this.clock = clock;
        this.audioService = audioService;
        this.stage = stage;
    }

    public void setup() {
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
        weatherConsumer.accept(currentWeather);

    }
    @Override
    public void update(float deltaTime) {

    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setWeatherConsumer(Consumer<Weather> weatherConsumer) {
        this.weatherConsumer = weatherConsumer;
    }
}
