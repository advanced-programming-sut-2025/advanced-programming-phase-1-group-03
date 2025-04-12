package phi.ap.model;

import phi.ap.model.enums.Time.WeekDays;

public class Date {
    private int hour;
    public int getDay(){

    }
    public int getSeason(){

    }
    public WeekDays getWeekDay(){}

    public int getHour() {
        return hour;
    }

    public Date(Date date) {
        this.hour = date.getHour();
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
