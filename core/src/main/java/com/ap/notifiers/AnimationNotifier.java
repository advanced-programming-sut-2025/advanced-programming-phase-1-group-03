package com.ap.notifiers;

import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationNotifier {
    public String atlasKey;
    public AtlasAsset atlasAsset;
    public int entityId;
    public float speed;
    public Animation.PlayMode playMode;
    public int engineId;

    public AnimationNotifier() {
    }

    public AnimationNotifier(String atlasKey, AtlasAsset atlasAsset, int entityId, int engineId, float speed, Animation.PlayMode playMode) {
        this.atlasKey = atlasKey;
        this.atlasAsset = atlasAsset;
        this.entityId = entityId;
        this.engineId = engineId;
        this.speed = speed;
        this.playMode = playMode;
    }
}
