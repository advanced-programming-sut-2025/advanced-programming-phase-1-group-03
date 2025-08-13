package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Shadow implements Component {
    public final static ComponentMapper<Shadow> mapper = ComponentMapper.getFor(Shadow.class);

    public boolean isChanged = true;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
