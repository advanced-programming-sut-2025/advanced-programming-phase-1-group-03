package com.ap.client.state;

import com.ap.client.component.Animation2D;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;



public enum FarmAnimalAnimationState implements State<Entity> {
    Idle,
    Walk,
    Sleep,
    Eat,
    Swim,
    Special,
    Pet
    ;


    @Override
    public void enter(Entity entity) {

    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void exit(Entity entity) {

    }

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        return false;
    }
}
