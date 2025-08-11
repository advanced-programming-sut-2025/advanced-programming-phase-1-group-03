package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Lake implements Component {
    public final static ComponentMapper<Lake> mapper = ComponentMapper.getFor(Lake.class);
}
