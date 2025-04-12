package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.List;

public enum Weather {
    Sunny(new ArrayList<>(List.of(Seasons.Fall, Seasons.Winter, Seasons.Spring, Seasons.Summer))),
    Rain(new ArrayList<>(List.of(Seasons.Fall, Seasons.Spring, Seasons.Summer))),
    Storm(new ArrayList<>(List.of(Seasons.Fall, Seasons.Spring, Seasons.Summer))),
    Snow(new ArrayList<>(List.of(Seasons.Winter)))
    ;


    Weather(ArrayList<Seasons> seasonList) {
        this.seasonList = seasonList;
    }

    private final ArrayList<Seasons> seasonList;

    public ArrayList<Seasons> getSeasonList() {
        return seasonList;
    }
}
