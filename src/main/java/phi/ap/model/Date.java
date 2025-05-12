package phi.ap.model;

import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.enums.Time.WeekDays;

public class Date {
    public static int START_HOUR = 9;
    public static int SLEEP_HOUR = 23;
    public static int SEASONS_DAYS = 28;

    private int hour;
    public int getDay(){
        return (hour/24)%SEASONS_DAYS + 1;
    }
    private int getRawDay(){
        return (hour/24) + 1;
    }
    public Seasons getSeason(){
        return Seasons.values()[(getRawDay()-1)/SEASONS_DAYS];
    }
    public Seasons getTomorrowSeason(){
        return Seasons.values()[(getRawDay() -1 + 1)/SEASONS_DAYS];
    }
    public WeekDays getWeekDay(){
        return WeekDays.values()[(getRawDay()-1)%7];
    }

    public int getHour() {
        return hour%24;
    }

    public Date(int hour) {
        this.hour = hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void goToNextDay() {
        this.hour += (START_HOUR - getHour() + 24);
    }

    public void advanceHourWithoutSleep(int hour){
        this.hour += hour;
    }
    //return if it's sleeping time or not
    public boolean advanceHour(){
        this.hour += 1;
        return this.getHour() == SLEEP_HOUR;
    }
}
