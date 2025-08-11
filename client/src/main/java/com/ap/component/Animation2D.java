package com.ap.component;

import com.ap.asset.AtlasAsset;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation2D implements Component {
    public final static ComponentMapper<Animation2D> mapper = ComponentMapper.getFor(Animation2D.class);

    private float stateTime = 0f;
    private AtlasAsset atlasAsset;
    private String atlasKey;
    private Animation.PlayMode playMode;
    private float speed;

    public Animation2D(AtlasAsset atlasAsset, String atlasKey, float speed, Animation.PlayMode playMode) {
        this.atlasAsset = atlasAsset;
        this.atlasKey = atlasKey;
        this.speed = speed;
        this.playMode = playMode;
    }

    public float incAndGet(float delta) {
        stateTime += delta * speed;
        return stateTime;
    }
    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public AtlasAsset getAtlasAsset() {
        return atlasAsset;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public void setAtlasKey(String atlasKey) {
        this.atlasKey = atlasKey;
    }

    public void set(AtlasAsset atlasAsset, String atlasKey, Animation.PlayMode playMode) {
        this.stateTime = 0f;
        this.atlasAsset = atlasAsset;
        this.atlasKey = atlasKey;
        this.playMode = playMode;
    }

    public float getSpeed() {
        return speed;
    }

    public Animation.PlayMode getPlayMode() {
        return playMode;
    }
}
