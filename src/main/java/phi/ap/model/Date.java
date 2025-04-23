package phi.ap.model;

import phi.ap.model.enums.Time.WeekDays;

public class Date {
    private int hour;
    public int getDay(){
        return -1;
    }
    public int getSeason(){
        return -1;
    }
    public WeekDays getWeekDay(){
        return null;
    }

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
