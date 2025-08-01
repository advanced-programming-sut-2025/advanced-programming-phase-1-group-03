package com.ap.system.universal;

import com.ap.Constraints;
import com.ap.model.Season;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.EntitySystem;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * This class calculate time and notify all of listeners
 */

public class TimeSystem extends EntitySystem {
    private static final int startHour = Constraints.START_HOUR;
    private static final int endHour = Constraints.END_HOUR;
    private static float gameSpeed = Constraints.GAME_SPEED;
    private static final int monthsDays = Constraints.MONTHS_DAYS;

    private ArrayList<ITimeListener> listeners = new ArrayList<>();

    private float timer = 0f;

    public TimeSystem() {
        // Make timer point to startHour
        timer = startHour * 3600;
    }

    @Override
    public void update(float deltaTime) {
        Time previousTime = getTime();
        timer += deltaTime * gameSpeed;
        notifyConsumer(previousTime);
    }

    private void notifyConsumer(Time previousTime) {
        Time currentTime = getTime();
        for(ITimeListener listener : listeners) {
            if(currentTime.season != previousTime.season) {
                listener.onSeasonChanged(currentTime.season);
            }
            if(currentTime.month != previousTime.month) {
                listener.onMonthChanged(currentTime.month);
            }
            if(currentTime.day != previousTime.day) {
                listener.onDayChanged(currentTime.day);
            }
            if(currentTime.hour != previousTime.hour) {
                listener.onHourChanged(currentTime.hour);
            }
            if(currentTime.minute != previousTime.minute) {
                listener.onMinuteChanged(currentTime.minute);
            }
            listener.onFrameChanged(currentTime);
        }
    }

    /**
     * This method will return the number of seconds passed from the beginning of the game
     * @return Int value
     */
    public int getTotalSeconds() {
        return (int) timer;
    }

    public static int totalSecondsFrom(int totalSeconds, int hour) {
        return totalSeconds % (24 * 60 * 60) - hour * (60 * 60);
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
        return (int) (timer / 60) % 60;
    }

    private int calculateHour(int totalSeconds) {
        return (int) (timer / 3600) % 24;
    }

    private Time getTime() {
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
        int totalSeconds = (int) timer;
        int monthsPassed = (totalSeconds / (24 * 60 * 60 * monthsDays));
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
        listener.onMinuteChanged(time.minute);
        listener.onSeasonChanged(time.season);
        listener.onFrameChanged(time);
        listener.onDayChanged(time.day);
        listener.onHourChanged(time.hour);
        listener.onMonthChanged(time.minute);
    }

    public static void setGameSpeed(float gameSpeed) {
        TimeSystem.gameSpeed = gameSpeed;
    }

    public void removeTimeListener(ITimeListener listener) {
        listeners.remove(listener);
    }
    public record Time(Season season, int month, int day, int hour, int minute, int totalSeconds){}
}
