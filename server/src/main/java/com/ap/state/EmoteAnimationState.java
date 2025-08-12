package com.ap.state;

import com.ap.component.Animation2D;
import com.ap.component.Emote;
import com.ap.component.Fsm;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum EmoteAnimationState implements State<Entity> {
    Opening,
    Closing,
    Heart,
    Sleep,
    Noise
    ;

    @Override
    public void enter(Entity entity) {
        Animation2D.mapper.get(entity).setAnimationType(Animation2D.AnimationType.valueOf("Emote_"+this.name()));
    }

    @Override
    public void update(Entity entity) {
        Emote emote = Emote.mapper.get(entity);

        if (!emote.getCurrentEmote().name().equals(this.name())) {
            Fsm.mapper.get(entity).getAnimationFsm().changeState(EmoteAnimationState.valueOf(emote.getCurrentEmote().name()));
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
