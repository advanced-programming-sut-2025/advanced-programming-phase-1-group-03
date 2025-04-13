package phi.ap.model.items.relations;

import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.enums.Weather;

public class Dialogue {
   private final Integer friendshipLevel;
   private final Integer hourOfDay;
   private final Seasons season;
   private final Weather weather;
   private final String message;

    public Dialogue(int friendshipLevel, int hourOfDay, Seasons season, Weather weather, String message) {
        this.friendshipLevel = friendshipLevel;
        this.hourOfDay = hourOfDay;
        this.season = season;
        this.weather = weather;
        this.message = message;
    }

    public Integer getFriendshipLevel() {
        return friendshipLevel;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public Seasons getSeason() {
        return season;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getMessage() {
        return message;
    }


}
