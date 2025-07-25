package com.ap.utils;

import com.ap.component.Physic;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

public class Helper {
    public static void removeEntity(Entity entity, Engine engine, World world) {
        if(Physic.mapper.has(entity)) {
            Physic physic = Physic.mapper.get(entity);
            world.destroyBody(physic.getBody());
        }
        engine.removeEntity(entity);
    }
}
