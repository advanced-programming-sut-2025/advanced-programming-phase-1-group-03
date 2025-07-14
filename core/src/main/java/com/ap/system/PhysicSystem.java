package com.ap.system;

import com.ap.component.Physic;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicSystem extends IteratingSystem {
    private World world;
    private float interval;
    private float accumulator = 0;

    public PhysicSystem(World world, float interval) {
        super(Family.all(Physic.class, Transform.class).get());
        this.world = world;
        this.interval = interval;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        Physic physic = Physic.mapper.get(entity);
        physic.getPreviousPosition().set(physic.getBody().getPosition());
    }

    @Override
    public void update(float deltaTime) {
        accumulator += deltaTime;

        while(accumulator >= interval) {
            accumulator -= interval;

            // Call processEntity for each entity
            super.update(interval);

            world.step(interval, 6, 2);
        }

        world.clearForces();

        // alpha is a number between 0 and 1
        float alpha = accumulator / interval;

        // We do this because we want smooth movement
        for(Entity entity : getEntities()) {
            updateTransform(entity, alpha);
        }
    }

    private void updateTransform(Entity entity, float alpha) {
        Transform transform = Transform.mapper.get(entity);
        Physic physic = Physic.mapper.get(entity);

        transform.getPosition().set(
                MathUtils.lerp(physic.getPreviousPosition().x, physic.getBody().getPosition().x, alpha),
                MathUtils.lerp(physic.getPreviousPosition().y, physic.getBody().getPosition().y, alpha)
        );
    }
}
