package com.ap.component;

import com.ap.asset.AtlasAsset;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation2D implements Component {
    public final static ComponentMapper<Animation2D> mapper = ComponentMapper.getFor(Animation2D.class);

    private AnimationType animationType;
    private Facing.FacingDirection facingDirection;
    private Animation<TextureRegion> animation;
    private Animation.PlayMode playMode;
    private float stateTime;
    private float speed;
    private boolean shouldUpdate;
    private AtlasAsset atlasAsset;
    private String atlastKey;

    public Animation2D(AtlasAsset atlas, String atlasKey,
                       AnimationType animationType, Animation.PlayMode playMode, float speed) {
        this.atlasAsset = atlas;
        this.atlastKey = atlasKey;
        this.animationType = animationType;
        this.playMode = playMode;
        this.speed = speed;

        this.shouldUpdate = true;
        this.stateTime = 0f;
        this.animation = null;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
        this.shouldUpdate = true;
    }

    public void setAnimation(Animation<TextureRegion> animation, Facing.FacingDirection facingDirection) {
        this.animation = animation;
        this.stateTime = 0f;
        this.facingDirection = facingDirection;
        this.shouldUpdate = false;
    }

    public float incAndGetStateTime(float deltaTime) {
        this.stateTime += deltaTime * speed;
        return this.stateTime;
    }

    public boolean shouldUpdate() {
        return shouldUpdate;
    }

    public Facing.FacingDirection getFacingDirection() {
        return facingDirection;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public Animation.PlayMode getPlayMode() {
        return playMode;
    }

    public String getAtlastKey() {
        return atlastKey;
    }

    public AtlasAsset getAtlasAsset() {
        return atlasAsset;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public enum AnimationType {
        Idle,
        Walk
        ;
        private final String atlasKey;
        AnimationType() {
            atlasKey = name().toLowerCase();
        }
        public String getAtlasKey() {
            return atlasKey;
        }
    }
}
