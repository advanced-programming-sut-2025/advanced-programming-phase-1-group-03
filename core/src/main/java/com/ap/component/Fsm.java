package com.ap.component;

import com.ap.state.AnimationState;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;

public class Fsm implements Component {
    public static final ComponentMapper<Fsm> mapper = ComponentMapper.getFor(Fsm.class);

    private final DefaultStateMachine<Entity, AnimationState> animationFsm;

    public Fsm(Entity owner) {
        animationFsm = new DefaultStateMachine<>(owner, AnimationState.Idle);
    }

    public DefaultStateMachine<Entity, AnimationState> getAnimationFsm() {
        return animationFsm;
    }
}
