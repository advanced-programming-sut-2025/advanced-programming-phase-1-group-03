package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class GreenhouseCmp implements Component {
    public final static ComponentMapper<GreenhouseCmp> mapper = ComponentMapper.getFor(GreenhouseCmp.class);
}
