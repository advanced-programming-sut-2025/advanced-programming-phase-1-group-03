package com.ap.client.component.items;

import com.ap.client.model.FarmAnimalTypes;
import com.ap.client.state.FarmAnimalAnimationState;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class FarmAnimal implements Component {
    public static final ComponentMapper<FarmAnimal> mapper = ComponentMapper.getFor(FarmAnimal.class);

    private Situation situation;
    private float animationStateTime;
    private final FarmAnimalTypes type;

    public FarmAnimal(FarmAnimalTypes type) {
        this.type = type;
        situation = Situation.Idle;
    }

    public Situation getSituation() {
        return situation;
    }

    public float getAnimationStateTime() {
        return animationStateTime;
    }

    public FarmAnimalTypes getType() {
        return type;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
        animationStateTime = 0;
    }

    public void setAnimationStateTime(float animationStateTime) {
        this.animationStateTime = animationStateTime;
    }

    public enum Situation {
        Idle(FarmAnimalAnimationState.Idle),
        Walk(FarmAnimalAnimationState.Walk),
        Sleep(FarmAnimalAnimationState.Sleep),
        Eat(FarmAnimalAnimationState.Eat),
        Pet(FarmAnimalAnimationState.Special),
        Swim(FarmAnimalAnimationState.Swim),
        ;

        public final FarmAnimalAnimationState animationState;

        Situation(FarmAnimalAnimationState animationState) {
            this.animationState = animationState;
        }
    }

}
