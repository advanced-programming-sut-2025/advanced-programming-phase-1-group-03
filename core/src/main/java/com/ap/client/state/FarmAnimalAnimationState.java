package com.ap.client.state;

import com.ap.client.component.Animation2D;
import com.ap.client.component.Fsm;
import com.ap.client.component.Move;
import com.ap.client.component.items.FarmAnimal;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;



public enum FarmAnimalAnimationState implements State<Entity> {
    Idle ,
    Walk,
    Sleep,
    Eat,
    Swim,
    Special
    ;


    @Override
    public void enter(Entity entity) {
        FarmAnimal animal = FarmAnimal.mapper.get(entity);
        Animation2D.mapper.get(entity).setAnimationType(
                Animation2D.AnimationType.valueOf(animal.getSituation().animationState.name()));
    }

    @Override
    public void update(Entity entity) {
        FarmAnimal animal = FarmAnimal.mapper.get(entity);
        if (animal.getSituation().animationState != this) {
            Fsm.mapper.get(entity).getAnimationFsm().changeState(animal.getSituation().animationState);
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
