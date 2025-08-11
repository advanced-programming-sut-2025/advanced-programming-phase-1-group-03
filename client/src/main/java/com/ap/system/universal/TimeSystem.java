package com.ap.system.universal;

import com.ap.model.Season;
import com.ap.model.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TimeSystem {
    private Time currentTime = new Time(Season.Spring, 0, 0,0,0,0);
    private final List<Consumer<Time>> consumers = new ArrayList<>();


    public void receive(Time time) {
        this.currentTime = time;
        for(Consumer<Time> listener : consumers) {
            listener.accept(time);
        }
    }

    public Season getSeason() {
        return currentTime.season();
    }

    public int getTotalSeconds() {
        return currentTime.totalSeconds();
    }

    public void addListener(Consumer<Time> consumer) {
        consumers.add(consumer);
        consumer.accept(currentTime);
    }
}
