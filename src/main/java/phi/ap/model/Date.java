package phi.ap.model;

import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.enums.Time.WeekDays;

import static java.lang.Math.floor;

public class Date {
    public static int START_HOUR = 9;
    public static int SLEEP_HOUR = 23;
    public static int SEASONS_DAYS = 28;
    public static int SLEEP_TIME = 10;

    private int hour;
    public int getDay(){
        return (hour/24)%SEASONS_DAYS + 1;
    }
    public int getRawDay(){
        return Math.floorDiv(hour, 24) + 1;
    }
    public Seasons getSeason(){
        return Seasons.values()[((getRawDay()-1)/SEASONS_DAYS)%4];
    }
    public Seasons getTomorrowSeason(){
        return Seasons.values()[((getRawDay() -1 + 1)/SEASONS_DAYS)%4];
    }
    public WeekDays getWeekDay(){
        return WeekDays.values()[(getRawDay()-1)%7];
    }

    public int getCurrentHour(){
        return hour%24;
    }
    public int getHour() {
        return hour;
    }
    public Date(int hour) {
        this.hour = hour;
    }
    public Date(Date date) {
        this.hour = date.getHour();
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void advanceHourRaw(int hour){

        this.hour += hour;
    }
    //return if it's sleeping time or not
    public int advanceHour(){
        this.hour += 1;
        if(getCurrentHour() == SLEEP_HOUR)
            return SLEEP_TIME;
        return 0;
    }

}
