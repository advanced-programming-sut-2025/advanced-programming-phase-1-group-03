package com.ap.model;

public class Time {
    private Season season;
    private int month;
    private int day;
    private int minute;
    private int totalSeconds;
    private int hour;

    public Time() {
    }

    public Time(Season season, int month, int day, int hour, int minute, int totalSeconds) {
        this.season = season;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.totalSeconds = totalSeconds;
    }

    public Season season() {
        return season;
    }

    public int month() {
        return month;
    }
    public int day() {
        return day;
    }
    public int minute() {
        return minute;
    }
    public int totalSeconds() {
        return totalSeconds;
    }

    public int hour() {
        return hour;
    }
}