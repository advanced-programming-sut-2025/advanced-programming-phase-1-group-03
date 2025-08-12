package com.ap.component;

import com.ap.model.EmoteType;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Emote implements Component {
    public static final ComponentMapper<Emote> mapper = ComponentMapper.getFor(Emote.class);

    public static final float openingTimeLimit = 0.5f;

    //transform that should attach
    private Transform father;
    private EmoteType currentEmote;
    private EmoteType emote;
    private float timeState;
    private float duration;

    //-1 duration for unlimited, for removing it set duration 0 again;
    public Emote(Transform father, EmoteType emote, float duration) {
        this.father = father;
        currentEmote = EmoteType.Opening;
        this.emote = emote;
        if (emote == EmoteType.Closing || emote == EmoteType.Opening) {
            this.emote = EmoteType.Noise;
        }
        timeState = 0;
        this.duration = duration;
    }

    public Transform getFather() {
        return father;
    }

    public EmoteType getCurrentEmote() {
        return currentEmote;
    }

    public float getTimeState() {
        return timeState;
    }

    public float getDuration() {
        return duration;
    }

    public EmoteType getEmote() {
        return emote;
    }

    public void setCurrentEmote(EmoteType currentEmote) {
        this.currentEmote = currentEmote;
    }

    public void setTimeState(float timeState) {
        this.timeState = timeState;
    }
}
