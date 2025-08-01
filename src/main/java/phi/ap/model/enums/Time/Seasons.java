package phi.ap.model.enums.Time;

import phi.ap.model.enums.Weather;

import java.util.ArrayList;
import java.util.Arrays;

public enum  Season {
    Spring("Spring"),
    Summer("Summer"),
    Fall("Fall"),
    Winter("Winter"),
    Special("Special")
    ;

    public String name;
     Season(String name) {
        this.name = name;
    }
    public final static int days = 28;

    public ArrayList<Weather> getPossibleWeathers() {
        ArrayList<Weather> res = new ArrayList<>();
        for (Weather weather : Weather.values()) {
            if (name.equals("Special")) {
                if (weather.getSeasonList().size() == 4 || weather.getSeasonList().contains( Season.Special)) {
                    res.add(weather);
                }
            } else {
                if (weather.getSeasonList().contains(this)) {
                    res.add(weather);
                }
            }
        }
        switch (name) {
            case "Spring":
                for (int i = 0; i < 3; i++) res.add(Weather.Sunny);
                for (int i = 0; i < 1; i++) res.add(Weather.Rain);
                break;
            case "Summer":
                for (int i = 0; i < 5; i++) res.add(Weather.Sunny);
                break;
            case "Fall":
                for (int i = 0; i < 3; i++) res.add(Weather.Rain);
                for (int i = 0; i < 2; i++) res.add(Weather.Storm);
            case "Winter":
                for (int i = 0; i < 5; i++) res.add(Weather.Snow);
                break;
        }
        return res;
    }

    public static ArrayList< Season> buildList( Season...  Season){
        return new ArrayList< Season>(Arrays.asList( Season));
    }
    public static ArrayList< Season> getAll(){
        return new ArrayList< Season>(Arrays.asList( Season.values()));
    }
    public boolean equals( Season season){
        return (name().equals(season.name()) || name().equals("Special") || season ==  Season.Special);
    }
}
