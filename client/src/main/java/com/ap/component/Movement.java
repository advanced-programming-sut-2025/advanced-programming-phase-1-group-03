package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Movement implements Component {
    public static final ComponentMapper<Movement> mapper = ComponentMapper.getFor(Movement.class);

    public final Vector2 prevPos = new Vector2();
    public final Vector2 targetPos = new Vector2();
    public float accumulator = 0f;
    public float interval = 0.1f;
}
