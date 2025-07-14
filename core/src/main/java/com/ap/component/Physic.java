package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Physic implements Component {
    public static final ComponentMapper<Physic> mapper = ComponentMapper.getFor(Physic.class);

    private final Body body;
    private Vector2 previousPosition;

    public Physic(Body body, Vector2 previousPosition) {
        this.body = body;
        this.previousPosition = previousPosition;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Vector2 previousPosition) {
        this.previousPosition = previousPosition;
    }
}
