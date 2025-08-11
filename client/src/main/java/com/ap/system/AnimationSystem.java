package com.ap.system;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.Animation2D;
import com.ap.component.Facing;
import com.ap.component.Facing.FacingDirection;
import com.ap.component.Graphic;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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
        super(Family.all(Animation2D.class).get());
        this.assetService = assetService;
        this.animationCache = new HashMap<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Animation2D animation2D = Animation2D.mapper.get(entity);

        final float stateTime = animation2D.incAndGet(deltaTime);
        var anim = updateAnimation(animation2D);
        anim.setPlayMode(animation2D.getPlayMode());
        TextureRegion textureRegion = anim.getKeyFrame(stateTime);
        Graphic.mapper.get(entity).setRegion(textureRegion);
    }

    // Assets must be in the following format:
    // atlasKey/type_facing.png

    private Animation<TextureRegion> updateAnimation(Animation2D animation2D) {
        AtlasAsset atlasAsset = animation2D.getAtlasAsset();
        String atlasKey = animation2D.getAtlasKey();
        CacheKey cacheKey = new CacheKey(atlasAsset, atlasKey);
        return animationCache.computeIfAbsent(cacheKey, key -> {
            TextureAtlas atlas = assetService.get(atlasAsset);
            var regions = atlas.findRegions(atlasKey);
            return new Animation<>(frameDuration, regions);
        });
    }

    private record CacheKey(
            AtlasAsset atlasAsset,
            String atlasKey
    ) {
    }
}
