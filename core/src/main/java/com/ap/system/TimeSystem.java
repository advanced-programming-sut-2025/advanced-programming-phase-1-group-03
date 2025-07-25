package com.ap.system;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.model.Season;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;

public class TimeSystem extends EntitySystem {
    private static final int startHour = Constraints.START_HOUR;
    private static final int endHour = Constraints.END_HOUR;
    private static final float gameSpeed = Constraints.GAME_SPEED;
    public static final int darknessBegin = Constraints.DARKNESS_BEGIN_HOUR;
    public static final int darknessEnd = Constraints.DARKNESS_END_HOUR;
    private static final int monthsDays = Constraints.MONTHS_DAYS;

    private float timer = 0f;
    private final Clock clock;

    private final String[] monthsShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public TimeSystem(Clock clock) {
        this.clock = clock;

        // Make timer point to startHour
        timer = startHour * 3600;

        clock.setTime(buildTime((int) timer));
        clock.setDate(buildDate((int) timer));
        clock.setSeason(getSeason());
    }

    @Override
    public void update(float deltaTime) {
        timer += deltaTime * gameSpeed;
        int totalSeconds = (int) timer;

        // It's time to sleep
        if(calculateHour(totalSeconds) >= endHour) {
            //TODO
        }

        // Every 10 minute notify the view
        if(calculateMinute(totalSeconds) % 10 == 0) {
            clock.setTime(buildTime(totalSeconds));
        }
        clock.setDate(buildDate(totalSeconds));

        float progress = totalSecondsFrom(totalSeconds, startHour) / ((endHour - startHour) * 60 * 60f);
        clock.setArrowAngle((int) (180 - (progress) * 180));

    }

    /**
     * This method will return the number of seconds passed from the beginning of the game
     * @return Int value
     */
    public int getTotalSeconds() {
        return (int) timer;
    }

    public int totalSecondsFrom(int totalSeconds, int hour) {
        return totalSeconds % (24 * 60 * 60) - hour * (60 * 60);
    }
    private String buildDate(int totalSeconds) {
        return String.valueOf(monthsShort[calculateMonthIndex(totalSeconds)])
                + " " + String.valueOf(calculateDay(totalSeconds));
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

    private String buildTime(int totalSeconds) {
        String hour = String.valueOf(calculateHour(totalSeconds));
        String minute = String.valueOf(calculateMinute(totalSeconds));
        if(hour.length() == 1) {
            hour = "0" + hour;
        }
        if(minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
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
//        return Season.Winter;
    }
}
