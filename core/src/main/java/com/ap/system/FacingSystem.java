package com.ap.system;

import com.ap.component.Crow;
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
//        System.out.println(entity);
        Crow crow = Crow.mapper.get(entity);
        if (crow == null) {
            Move move = Move.mapper.get(entity);

            Vector2 direction = move.getDirection();
            if (direction.isZero())
                return;

            Facing facing = Facing.mapper.get(entity);
            if (direction.y > 0) {
                facing.setDirection(Facing.FacingDirection.Up);
            } else if (direction.y < 0) {
                facing.setDirection(Facing.FacingDirection.Down);
            } else if (direction.x > 0) {
                facing.setDirection(Facing.FacingDirection.Right);
            } else if (direction.x < 0) {
                facing.setDirection(Facing.FacingDirection.Left);
            }
        } else {
//            if (crow.getSpeed() == 0) return;
            Facing.FacingDirection facingDirection;
            Facing facing = Facing.mapper.get(entity);
            float direction = crow.getDirection();
            direction = (direction + 360) % 360;
            if (direction <= 45 || direction > 315) facing.setDirection(facingDirection = Facing.FacingDirection.Right);
            else if (direction > 45 && direction <= 135) facing.setDirection(facingDirection = Facing.FacingDirection.Up);
            else if (direction > 135 && direction <= 225) facing.setDirection(facingDirection = Facing.FacingDirection.Left);
            else facing.setDirection(facingDirection = Facing.FacingDirection.Down);
//            System.out.println("*facingDirection: " + facingDirection);
        }
    }
}
