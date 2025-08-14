package com.ap.system.universal;

import com.ap.Constraints;
import com.ap.model.Season;
import com.ap.model.Time;
import com.badlogic.ashley.core.EntitySystem;

import java.util.ArrayList;

/**
 * This class calculate time and notify all of listeners
 */

public class TimeSystem extends EntitySystem {
    private static final int startHour = Constraints.START_HOUR;
    private static final int endHour = Constraints.END_HOUR;
    private static float gameSpeed = Constraints.GAME_SPEED;
    private static final int monthsDays = Constraints.MONTHS_DAYS;

    private ArrayList<ITimeListener> listeners = new ArrayList<>();

    float timer = 0f;

    public TimeSystem() {
        timer = startHour * 60 * 60;
    }
    @Override
    public void update(float deltaTime) {
        Time previousTime = getTime();
        timer += deltaTime * gameSpeed;
        if(previousTime.hour() == endHour) {
            timer += (startHour - endHour + 24) * 60 * 60;
        }
        notifyConsumer(previousTime);
    }

    private void notifyConsumer(Time previousTime) {
        Time currentTime = getTime();
        for(ITimeListener listener : listeners) {
            if(currentTime.season() != previousTime.season()) {
                listener.onSeasonChanged(currentTime.season());
            }
            if(currentTime.month() != previousTime.month()) {
                listener.onMonthChanged(currentTime.month());
            }
            if(currentTime.hour() != previousTime.hour()) {
                listener.onHourChanged(currentTime.hour());
            }
            if(currentTime.minute() != previousTime.minute()) {
                listener.onMinuteChanged(currentTime.minute());
            }
            if(currentTime.day() != previousTime.day()) {
                listener.onDayChanged(currentTime.day());
            }
        }
    }


    private int calculateDay(int totalSeconds) {
        int daysPassed = (totalSeconds / (24 * 60 * 60));
        return daysPassed % 30 + 1;
    }

    private int calculateMonthIndex(int totalSeconds) {
        int daysPassed = (totalSeconds / (24 * 60 * 60));
        return (daysPassed / 30) % 12;
    }

    private int calculateMinute(int totalSeconds) {
        return (totalSeconds / 60) % 60;
    }

    private int calculateHour(int totalSeconds) {
        return (totalSeconds / 3600) % 24;
    }

    public Time getTime() {
        int totalSeconds = getTotalSeconds();
        return new Time(
                getSeason(),
                calculateMonthIndex(totalSeconds),
                calculateDay(totalSeconds),
                calculateHour(totalSeconds),
                calculateMinute(totalSeconds),
                totalSeconds
        );
    }


    public Season getSeason() {
        int monthsPassed = (getTotalSeconds() / (24 * 60 * 60 * monthsDays));
        monthsPassed %= 12;
        if(monthsPassed <= 2) {
            return Season.Spring;
        }
        else if(monthsPassed <= 5) {
            return Season.Summer;
        }
        else if(monthsPassed <= 8) {
            return Season.Fall;
        }
        else {
            return Season.Winter;
        }
    }

    public void addTimeListener(ITimeListener listener) {
        listeners.add(listener);
        Time time = getTime();
        listener.onMinuteChanged(time.minute());
        listener.onSeasonChanged(time.season());
        listener.onDayChanged(time.day());
        listener.onHourChanged(time.hour());
        listener.onMonthChanged(time.minute());
    }

    public static void setGameSpeed(float gameSpeed) {
        TimeSystem.gameSpeed = gameSpeed;
    }

    public void removeTimeListener(ITimeListener listener) {
        listeners.remove(listener);
    }

    public int getTotalSeconds() {
        return (int) timer;
    }
}
