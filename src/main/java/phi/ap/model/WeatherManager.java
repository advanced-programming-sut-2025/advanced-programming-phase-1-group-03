package phi.ap.model;

import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.enums.Weather;
import phi.ap.model.items.Water;

import java.util.ArrayList;

public class WeatherManager {
    private Weather currentWeather = null;
    private Weather tomorrowWeather = null;

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setTomorrowWeather(Weather tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }

    public Weather getRandomWeather(Seasons seasons) {
        ArrayList<Weather> list = seasons.getPossibleWeathers();
        int ind = App.getInstance().getRandomNumber(0, list.size() - 1);
        return list.get(ind);
    }

    public void setWeathersInMorning() {
        if (tomorrowWeather != null) {
            setCurrentWeather(tomorrowWeather);
        } else {
            setCurrentWeather(getRandomWeather(Game.getInstance().getDate().getSeason()));
        }
        setTomorrowWeather(getRandomWeather(Game.getInstance().getDate().getTomorrowSeason()));
        currentWeather = Weather.Rain;
//        currentWeather = Weather.Storm;
    }
}
