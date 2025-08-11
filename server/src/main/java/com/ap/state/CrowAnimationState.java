package com.ap.state;

import com.ap.component.Animation2D;
import com.ap.component.Crow;
import com.ap.component.Fsm;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum CrowAnimationState implements State<Entity> {
    Walk,
    Voice,
    Fly,
    ;
    @Override
    public void enter(Entity entity) {
        Animation2D.mapper.get(entity).setAnimationType(Animation2D.AnimationType.valueOf(name()));
    }

    @Override
    public void update(Entity entity) {
        Crow crow = Crow.mapper.get(entity);
        if (!crow.getSituation().name().equals(name())) {
            Fsm.mapper.get(entity).getAnimationFsm().changeState(CrowAnimationState.valueOf(crow.getSituation().name()));
        }
    }

    @Override
    public void exit(Entity entity) {

    }

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        return false;
    }

}
