package com.ap.component.items;

import com.ap.model.BarnsType;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Barn implements Component {
    public static final ComponentMapper<Barn> mapper = ComponentMapper.getFor(Barn.class);
    private BarnsType type;

    public Barn(BarnsType type) {
        this.type = type;
    }

    public BarnsType getType() {
        return type;
    }
}
