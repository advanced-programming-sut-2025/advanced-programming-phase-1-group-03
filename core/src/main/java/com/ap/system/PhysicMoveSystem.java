package com.ap.system;

import com.ap.component.Move;
import com.ap.component.Physic;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicMoveSystem extends IteratingSystem {

    // We define this globally because we don't want to create a vector in every frame
    Vector2 speedVec = new Vector2();

    public PhysicMoveSystem() {
        super(Family.all(Physic.class, Move.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        Move move = Move.mapper.get(entity);
        Physic physic = Physic.mapper.get(entity);

        Body body = physic.getBody();

        if(move.isRooted() || move.getDirection().isZero()) {
            // Direction is zero or rooted -> we should stop movement
            body.setLinearVelocity(Vector2.Zero);
            return;
        }
        float maxSpeed = move.getMaxSpeed();
        speedVec.set(move.getDirection()).nor();
        body.setLinearVelocity(speedVec.x * maxSpeed, speedVec.y * maxSpeed);
    }
}
