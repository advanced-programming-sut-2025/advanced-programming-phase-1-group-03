package com.ap.system;

import com.ap.component.Facing;
import com.ap.component.Move;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class FacingSystem extends IteratingSystem {
    public FacingSystem() {
        super(Family.all(Move.class, Facing.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.mapper.get(entity);

        Vector2 direction = move.getDirection();
        if(direction.isZero())
            return;

        Facing facing = Facing.mapper.get(entity);
        if(direction.y > 0) {
            facing.setDirection(Facing.FacingDirection.Up);
        } else if(direction.y < 0) {
            facing.setDirection(Facing.FacingDirection.Down);
        } else if(direction.x > 0) {
            facing.setDirection(Facing.FacingDirection.Right);
        } else if(direction.x < 0) {
            facing.setDirection(Facing.FacingDirection.Left);
        }
    }
}
