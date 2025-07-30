package com.ap.component;

import com.ap.state.AnimationState;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;

public class Fsm implements Component {
    public static final ComponentMapper<Fsm> mapper = ComponentMapper.getFor(Fsm.class);

    private final DefaultStateMachine<Entity, State<Entity>> animationFsm;

    public Fsm(Entity owner, State<Entity> state) {
        animationFsm = new DefaultStateMachine<>(owner, state);
    }

    public DefaultStateMachine<Entity, State<Entity>> getAnimationFsm() {
        return animationFsm;
    }
}
