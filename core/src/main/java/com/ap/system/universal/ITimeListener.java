package com.ap.system.universal;

import com.ap.model.Season;

public interface ITimeListener {
    default void onSeasonChanged(Season newSeason){
    };
    default void onMonthChanged(int newMonthIndex){
    };
    default void onDayChanged(int newDay){
    };
    default void onHourChanged(int hour){
    };
    default void onMinuteChanged(int minute){
    };
    default void onFrameChanged(TimeSystem.Time newTime){
    };
}
