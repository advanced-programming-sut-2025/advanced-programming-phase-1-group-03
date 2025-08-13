package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Carrier implements Component {
    public final static ComponentMapper<Carrier> mapper = ComponentMapper.getFor(Carrier.class);

    private boolean canPlace;
    private boolean isChanged;

    public boolean isChanged() {
        return isChanged;
    }

    public void setCanPlace(boolean canPlace) {
        if(canPlace != this.canPlace) {
            setChanged(true);
        }
        this.canPlace = canPlace;
    }

    public boolean canPlace() {
        return canPlace;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public Carrier() {
    }

    public void set(Carrier carrier) {
        this.canPlace = carrier.canPlace;
    }
}