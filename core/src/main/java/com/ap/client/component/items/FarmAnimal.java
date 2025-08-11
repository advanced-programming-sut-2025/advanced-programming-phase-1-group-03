package com.ap.client.component.items;

import com.ap.client.items.Animals.Animal;
import com.ap.client.managers.AnimalManager;
import com.ap.client.model.FarmAnimalTypes;
import com.ap.client.state.FarmAnimalAnimationState;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class FarmAnimal implements Component {
    public static final ComponentMapper<FarmAnimal> mapper = ComponentMapper.getFor(FarmAnimal.class);

    private Situation situation;
    private float animationStateTime;
    private FarmAnimalTypes type;
    private final Animal animal;

    private float duration;

    private Vector2 direction = new Vector2(0, 0);

    private boolean isClicked = false;
    private int buttonClicked;

    public FarmAnimal(FarmAnimalTypes type, Animal animal) {
        this.type = type;
        situation = Situation.Idle;
        this.animal = animal;
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
        duration = situation.timeLimit;
    }

    //-1 for unlimited, after limit exceed turn to idle by default
    public void setSituation(Situation situation, float duration) {
        this.situation = situation;
        animationStateTime = 0;
        this.duration = duration;
    }

    public void setAnimationStateTime(float animationStateTime) {
        this.animationStateTime = animationStateTime;
    }

    public enum Situation {
        Idle(FarmAnimalAnimationState.Idle, -1),
        Walk(FarmAnimalAnimationState.Walk, -1),
        Sleep(FarmAnimalAnimationState.Sleep, -1),
        Eat(FarmAnimalAnimationState.Eat, 3),
        Pet(FarmAnimalAnimationState.Special, 2),
        Swim(FarmAnimalAnimationState.Swim, -1),
        ;

        public final FarmAnimalAnimationState animationState;

        //-1 for unlimited, after limit exceed turn to idle by default
        public final float timeLimit;

        Situation(FarmAnimalAnimationState animationState, float timeLimit) {
            this.animationState = animationState;
            this.timeLimit = timeLimit;
        }


    }

    public Animal getAnimal() {
        return animal;
    }

    public float getDuration() {
        return duration;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public void setButtonClicked(int buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public int getButtonClicked() {
        return buttonClicked;
    }

    public void setType(FarmAnimalTypes type) {
        this.type = type;
    }
}
