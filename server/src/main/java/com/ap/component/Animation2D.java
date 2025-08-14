package com.ap.component;

import com.ap.asset.AtlasAsset;
import com.ap.model.EmoteType;
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

    public void setPlayMode(Animation.PlayMode playMode) {
        this.playMode = playMode;
        shouldUpdate = true;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        shouldUpdate = true;
    }

    public void setAtlasKey(String atlasKey) {
        this.atlasKey = atlasKey;
        shouldUpdate = true;
    }

    public void setAtlasAsset(AtlasAsset atlasAsset) {
        this.atlasAsset = atlasAsset;
        shouldUpdate = true;
    }


    public enum AnimationType {
        Idle(true),
        Walk(true),
        Voice(true),
        Fly(true),
        Eat(false),
        Sleep(true),
        Special(true),
        Swim(true),
        Emote_Opening(false, "emote_" + EmoteType.Opening.index),
        Emote_Closing(false, "emote_" + EmoteType.Closing.index),
        Emote_Heart(false, "emote_" + EmoteType.Heart.index),
        Emote_Noise(false,  "emote_" + EmoteType.Noise.index),
        Emote_Sleep(false, "emote_" + EmoteType.Sleep.index),
        ;
        private final String atlasKey;
        private boolean hasFacing;
        AnimationType(boolean hasFacing) {
            atlasKey = name().toLowerCase();
            this.hasFacing = hasFacing;
        }
        AnimationType(boolean hasFacing, String atlasKey) {
            this.atlasKey = atlasKey;
            this.hasFacing = hasFacing;
        }
        public String getAtlasKey() {
            return atlasKey;
        }

        public boolean isHasFacing() {
            return hasFacing;
        }
    }
}
