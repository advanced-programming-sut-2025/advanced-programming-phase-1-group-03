package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Dirt implements Component {
    public static ComponentMapper<Dirt> mapper = ComponentMapper.getFor(Dirt.class);

    private boolean isPlowed;

    public Dirt(boolean isPlowed) {
        this.isPlowed = isPlowed;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }
}
