package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Carrier implements Component {
    public final static ComponentMapper<Carrier> mapper = ComponentMapper.getFor(Carrier.class);
}