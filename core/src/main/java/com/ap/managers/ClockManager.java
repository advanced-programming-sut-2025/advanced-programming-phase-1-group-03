package com.ap.managers;

import com.ap.Constraints;
import com.ap.model.Season;
import com.ap.system.universal.TimeSystem;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.EntitySystem;

public class ClockManager  {
    private static final int startHour = Constraints.START_HOUR;
    private static final int endHour = Constraints.END_HOUR;

    private final Clock clock;

    private final String[] monthsShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public ClockManager(Clock clock) {
        this.clock = clock;
    }


    private String buildDate(int monthIndex, int day) {
        return String.valueOf(monthsShort[monthIndex])
                + " " + String.valueOf(day);
    }

    private String buildTime(int hourInt, int minuteInt) {
        String hour = String.valueOf(hourInt);
        String minute = String.valueOf(minuteInt);
        if(hour.length() == 1) {
            hour = "0" + hour;
        }
        if(minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    public void receive(TimeSystem.Time time) {
        clock.setDate(buildDate(time.month(), time.day()));
        if(time.minute() % 10 == 0) {
            clock.setTime(buildTime(time.hour(), time.minute()));
        }
        float progress = TimeSystem.totalSecondsFrom(time.totalSeconds(), startHour) / ((endHour - startHour) * 60 * 60f);
        clock.setArrowAngle((int) (180 - (progress) * 180));
        clock.setSeason(time.season());
    }

}
