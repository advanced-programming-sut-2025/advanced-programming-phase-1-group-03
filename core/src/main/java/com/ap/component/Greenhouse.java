package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Greenhouse implements Component {
    public static ComponentMapper<Greenhouse> mapper = ComponentMapper.getFor(Greenhouse.class);

    private boolean isBuilt = false;
    public Greenhouse() {

    }

    public void setBuilt(boolean built) {
        isBuilt = built;
    }

    public boolean isBuilt() {
        return isBuilt;
    }
}
