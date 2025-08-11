package com.ap.system;

import com.ap.component.Move;
import com.ap.component.Movement;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SmoothMovementSystem extends IteratingSystem {

    public SmoothMovementSystem() {
        super(Family.all(Transform.class, Movement.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform t = Transform.mapper.get(entity);
        Movement m = Movement.mapper.get(entity);

        m.accumulator += deltaTime;
        float alpha = MathUtils.clamp(m.accumulator / m.interval, 0f, 1f);

        t.getPosition().set(
                MathUtils.lerp(m.prevPos.x, m.targetPos.x, alpha),
                MathUtils.lerp(m.prevPos.y, m.targetPos.y, alpha)
        );
    }
}
