package com.ap.system;

import com.ap.component.Fsm;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * This system just update FSMs
 */
public class FsmUpdateSystem extends IteratingSystem {
    public FsmUpdateSystem() {
        super(Family.all(Fsm.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Fsm fsm = Fsm.mapper.get(entity);
        fsm.getAnimationFsm().update();
    }
}
