package com.ap.system.universal;

import com.ap.Constraints;
import com.ap.model.Season;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.EntitySystem;

import java.util.function.Consumer;

public class TimeSystem extends EntitySystem {
    private static final int startHour = Constraints.START_HOUR;
    private static final int endHour = Constraints.END_HOUR;
    private static final float gameSpeed = Constraints.GAME_SPEED;
    private static final int monthsDays = Constraints.MONTHS_DAYS;

    private float timer = 0f;
    private Consumer<Time> timeConsumer;

    public TimeSystem() {

    }

    public void setup() {
        // Make timer point to startHour
        timer = startHour * 3600;

        notifyConsumer();
    }

    @Override
    public void update(float deltaTime) {
        timer += deltaTime * gameSpeed;
        notifyConsumer();
    }

    private void notifyConsumer() {
        int totalSeconds = getTotalSeconds();

        if(timeConsumer == null) {
            return;
        }

        timeConsumer.accept(new Time(
                getSeason(),
                calculateMonthIndex(totalSeconds),
                calculateDay(totalSeconds),
                calculateHour(totalSeconds),
                calculateMinute(totalSeconds),
                totalSeconds
        ));
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

    public Season getSeason() {
        return Season.Fall;
//        int totalSeconds = (int) timer;
//        int monthsPassed = (totalSeconds / (24 * 60 * 60 * monthsDays));
//        monthsPassed %= 12;
//        if(monthsPassed <= 2) {
//            return Season.Spring;
//        }
//        else if(monthsPassed <= 5) {
//            return Season.Summer;
//        }
//        else if(monthsPassed <= 8) {
//            return Season.Fall;
//        }
//        else {
//            return Season.Winter;
//        }
    }

    public void setTimeConsumer(Consumer<Time> timeConsumer) {
        this.timeConsumer = timeConsumer;
    }

    public record Time(Season season, int month, int day, int hour, int minute, int totalSeconds){}
}
