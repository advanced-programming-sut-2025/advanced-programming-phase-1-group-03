package com.ap.system;

import com.ap.model.Season;
import com.ap.model.Weather;
import com.ap.ui.widget.Clock;
import com.ap.notifiers.WeatherNotifier;
import com.badlogic.ashley.core.EntitySystem;

import java.util.function.Consumer;

public class WeatherSystem extends EntitySystem {
    private Weather currentWeather;
    private final Clock clock;
    private Consumer<Weather> weatherConsumer;

    public WeatherSystem(final Clock clock) {
        this.clock = clock;
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

    public void receive(WeatherNotifier weatherNotifier) {
        currentWeather = weatherNotifier.weather;
        if (weatherConsumer != null) {
            weatherConsumer.accept(currentWeather);
        }
        clock.setWeather(getCurrentWeather());
    }
}
