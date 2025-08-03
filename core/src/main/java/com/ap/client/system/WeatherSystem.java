package com.ap.client.system;

import com.ap.client.model.Season;
import com.ap.client.model.Weather;
import com.ap.client.system.universal.TimeSystem;
import com.ap.client.ui.widget.Clock;
import com.badlogic.ashley.core.EntitySystem;

import java.util.Random;
import java.util.function.Consumer;

public class WeatherSystem extends EntitySystem {
    private Weather currentWeather = null;
    private final Clock clock;
    private final TimeSystem timeSystem;
    private Consumer<Weather> weatherConsumer;

    public WeatherSystem(final Clock clock,
                         TimeSystem timeSystem) {

        this.timeSystem = timeSystem;
        this.clock = clock;
    }

    /**
     * This method set the weather to one of possible weathers randomly
     */
    public void setWeatherRandomly() {
        Season season = timeSystem.getSeason();
        int length = season.getPossibleWeathers().size();
        currentWeather = season.getPossibleWeathers().get(new Random().nextInt(length));

        if(weatherConsumer != null) {
            weatherConsumer.accept(currentWeather);
        }

        clock.setWeather(getCurrentWeather());
    }
    @Override
    public void update(float deltaTime) {

    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setWeatherConsumer(Consumer<Weather> weatherConsumer) {
        this.weatherConsumer = weatherConsumer;
        if(currentWeather != null && weatherConsumer != null) {
            weatherConsumer.accept(currentWeather);
        }
    }
}
