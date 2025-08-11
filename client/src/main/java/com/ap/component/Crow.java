package com.ap.component;

import com.ap.Constraints;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Crow implements Component {
    public static final ComponentMapper<Crow> mapper = ComponentMapper.getFor(Crow.class);

    public final static float playerNoticingRadius = 4f;
    public final static float walkingRadius = 0.5f;

    public Crow(Entity purposeEntity, Entity crowEntity, AudioService audioService) {
        this.purposeEntity = purposeEntity;
        this.crowEntity = crowEntity;
        this.audioService = audioService;
        setSituation(Situation.Voice);
    }

    private final Entity purposeEntity;
    private final Entity crowEntity;
    private final AudioService audioService;
    private Situation situation;
    private float direction = 180; //Degree base
    private float speed;
    private float stateTime;
    private boolean hasEaten = false;

    public void setSituation(Situation situation) {
        this.situation = situation;
        if (situation == Situation.Voice) {
            audioService.playSound(SoundAsset.CrowVoice);
        }
        if (situation == Situation.Fly) {
            audioService.playSound(SoundAsset.BirdFlying);
        }
        speed = situation.speed;
        stateTime = 0f;
        calculateDirection();
    }

    public void calculateDirection() {
        if (situation == Situation.Fly) {
            direction = Helper.random(130, 230);
        } else if (situation == Situation.Walk) {
            Vector2 purposePosition = Transform.mapper.get(purposeEntity).getPosition();
            Vector2 crowPosition = Transform.mapper.get(crowEntity).getPosition();
            if (Helper.calculateDistance(purposePosition, crowPosition) >= walkingRadius) {
                direction = MathUtils.atan2(purposePosition.y - crowPosition.y, purposePosition.x - crowPosition.x);
                direction *= MathUtils.radiansToDegrees;
            } else {
                direction = Helper.random(0, 359);
            }
        }
    }

    public float getDirection() {
        return direction;
    }

    public Situation getSituation() {
        return situation;
    }

    public Entity getPurposeEntity() {
        return purposeEntity;
    }

    public float getSpeed() {
        return speed;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public boolean isHasEaten() {
        return hasEaten;
    }

    public void setHasEaten(boolean hasEaten) {
        this.hasEaten = hasEaten;
    }

    public enum Situation {
        Walk(0.5f, 2),
        Voice(0, 0.5f),
        Fly(3f, Constraints.WORLD_WIDTH / 2f);
        public final float speed;
        public final float stateTimeLimit;
        Situation(float speed, float stateTimeLimit) {
            this.speed = speed;
            this.stateTimeLimit = stateTimeLimit;
        }

    }

}
