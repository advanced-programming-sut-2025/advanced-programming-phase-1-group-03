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
    private Animation.PlayMode playMode;
    private float speed;
    private boolean shouldUpdate;
    private AtlasAsset atlasAsset;
    private String atlasKey;

    public Animation2D(AtlasAsset atlas, String atlasKey,
                       AnimationType animationType, Animation.PlayMode playMode, float speed) {
        this.atlasAsset = atlas;
        this.atlasKey = atlasKey;
        this.animationType = animationType;
        this.playMode = playMode;
        this.speed = speed;

        this.shouldUpdate = true;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
        this.shouldUpdate = true;
    }

    public void setAnimation(Facing.FacingDirection facingDirection) {
        this.facingDirection = facingDirection;
        this.shouldUpdate = false;
    }

    public boolean shouldUpdate() {
        return shouldUpdate;
    }

    public Facing.FacingDirection getFacingDirection() {
        return facingDirection;
    }


    public String getAtlasKey() {
        return atlasKey;
    }

    public AtlasAsset getAtlasAsset() {
        return atlasAsset;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public float getSpeed() {
        return speed;
    }

    public Animation.PlayMode getPlayMode() {
        return playMode;
    }

    public enum AnimationType {
        Idle,
        Walk,
        Voice,
        Fly
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
