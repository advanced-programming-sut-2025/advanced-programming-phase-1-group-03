package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.List;

public enum Weather {
    Sunny(new ArrayList<>(Seasons.buildList(Seasons.Fall, Seasons.Winter, Seasons.Spring, Seasons.Summer))),
    Rain(new ArrayList<>(Seasons.buildList(Seasons.Fall, Seasons.Spring, Seasons.Summer))),
    Storm(new ArrayList<>(Seasons.buildList(Seasons.Fall, Seasons.Spring, Seasons.Summer))),
    Snow(new ArrayList<>(Seasons.buildList(Seasons.Winter)))
    ;


    Weather(ArrayList<Seasons> seasonList) {
        this.seasonList = seasonList;
    }

    private final ArrayList<Seasons> seasonList;

    public ArrayList<Seasons> getSeasonList() {
        return seasonList;
    }

    public static Weather getWeatherByName(String name) {
        name = name.toLowerCase();
        for (Weather weather : Weather.values()) {
            if (weather.name().toLowerCase().equals(name)) {
                return weather;
            }
        }
        return null;
    }

    public static void doThunderStorm(int x, int y){

    }
    public static void randomThunderStorm(){

    }
    public static Weather weatherForecast(){
        return null;
    }

}
