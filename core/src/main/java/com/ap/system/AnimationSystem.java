package com.ap.system;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.component.Animation2D;
import com.ap.component.Facing;
import com.ap.component.Facing.FacingDirection;
import com.ap.component.Graphic;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationSystem extends IteratingSystem {
    private final float frameDuration = Constraints.PLAYER_ANIMATION_FRAME_DURATION;

    private final AssetService assetService;
    private final Map<CacheKey, Animation<TextureRegion>> animationCache;
    public AnimationSystem(AssetService assetService) {
        super(Family.all(Animation2D.class, Facing.class, Graphic.class).get());
        this.assetService = assetService;
        this.animationCache = new HashMap<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Animation2D animation2D = Animation2D.mapper.get(entity);
        FacingDirection facing = Facing.mapper.get(entity).getDirection();

        final float stateTime;
        if(animation2D.shouldUpdate() || animation2D.getFacingDirection() != facing) {
            updateAnimation(animation2D, facing);
            stateTime = 0f;
        } else {
            stateTime = animation2D.incAndGetStateTime(deltaTime);
        }

        Animation<TextureRegion> animation = animation2D.getAnimation();
        animation.setPlayMode(animation2D.getPlayMode());
        TextureRegion textureRegion = animation.getKeyFrame(stateTime);
        Graphic.mapper.get(entity).setTexture(textureRegion);
    }

    // Assets must be in the following format:
    // atlasKey/type_facing.png

    private void updateAnimation(Animation2D animation2D, FacingDirection facing) {
        AtlasAsset atlasAsset = animation2D.getAtlasAsset();
        String atlasKey = animation2D.getAtlastKey();
        Animation2D.AnimationType type = animation2D.getAnimationType();
        CacheKey cacheKey = new CacheKey(atlasAsset, atlasKey, type, facing);
        Animation<TextureRegion> animation = animationCache.computeIfAbsent(cacheKey, key -> {
            TextureAtlas atlas = assetService.get(atlasAsset);
            String combinedKey = (!atlasKey.isEmpty() ? atlasKey+ "/" : "") +type.getAtlasKey() + "_" + facing.getAtlasKey();
            var regions = atlas.findRegions(combinedKey);
            return new Animation<>(frameDuration, regions);
        });
        animation2D.setAnimation(animation, facing);
    }

    private record CacheKey(
            AtlasAsset atlasAsset,
            String atlasKey,
            Animation2D.AnimationType type,
            FacingDirection direction
    ) {
    }
}
