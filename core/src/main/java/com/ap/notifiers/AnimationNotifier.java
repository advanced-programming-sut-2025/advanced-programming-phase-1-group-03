package com.ap.notifiers;

import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationNotifier {
    public String atlasKey;
    public AtlasAsset atlasAsset;
    public int entityId;
    public float speed;
    public Animation.PlayMode playMode;
    public AnimationNotifier() {
    }

    public AnimationNotifier(String atlasKey, AtlasAsset atlasAsset, int entityId, float speed, Animation.PlayMode playMode) {
        this.atlasKey = atlasKey;
        this.atlasAsset = atlasAsset;
        this.entityId = entityId;
        this.speed = speed;
        this.playMode = playMode;
    }
}
