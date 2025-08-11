package com.ap.system.universal;

import com.ap.model.Weather;
import com.ap.model.Season;
import com.ap.notifiers.WeatherNotifier;
import com.ap.model.Room;

import java.util.Random;

public class WeatherSystem {
    private Weather currentWeather = null;
    private final TimeSystem timeSystem;
    private final Room room;

    public WeatherSystem(TimeSystem timeSystem, Room room) {
        this.timeSystem = timeSystem;
        this.room = room;
        setWeatherRandomly();
    }

    /**
     * This method set the weather to one of possible weathers randomly
     */
    public void setWeatherRandomly() {
        Season season = timeSystem.getSeason();
        int length = season.getPossibleWeathers().size();
        currentWeather = season.getPossibleWeathers().get(new Random().nextInt(length));

        room.broadcast(new WeatherNotifier(currentWeather));
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }
}
