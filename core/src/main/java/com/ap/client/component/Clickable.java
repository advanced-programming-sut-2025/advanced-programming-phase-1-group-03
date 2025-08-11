package com.ap.client.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Clickable implements Component {
    public static final ComponentMapper<Clickable> clickableMapper = ComponentMapper.getFor(Clickable.class);

    public Clickable() {
    }
}
