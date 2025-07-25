package com.ap.ui.effects;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.TextureAsset;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Rain extends Actor {
    private final Array<Drop> drops = new Array<>();

    // 10 per second
    private static final float spawnRate = 1 / 50f;
    private float timer = 0f;
    private static final float speed = 30 * 60;
    private static final float animationSpeed = 1 / 10f;
    private static  final float scale = 1f;

    private final TextureRegion[][] rainTextures;

    public Rain(AssetService assetService) {
        Texture rainTexture = assetService.get(TextureAsset.Rain);
        rainTextures = TextureRegion.split(rainTexture, rainTexture.getWidth() / 4, rainTexture.getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Drop drop : drops) {
            batch.draw(drop.region,
                    drop.position.x, drop.position.y,
                    0, 0,
                    drop.region.getRegionWidth(), drop.region.getRegionHeight(),
                    scale, scale,
                    0);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        timer += delta;

        if(timer >= spawnRate) {
            timer = 0f;

            int x = new Random().nextInt(Constraints.WORLD_WIDTH_RESOLUTION);
            int y = Constraints.WORLD_HEIGHT_RESOLUTION;
            int targetY = new Random().nextInt(Constraints.WORLD_HEIGHT_RESOLUTION);
            int type = new Random().nextInt(2);

            drops.add(new Drop(
                    new Vector2(x, y), targetY, rainTextures[type][0],
                    true,
                    new Animation<>(animationSpeed, rainTextures[type])));
        }

        for (Drop drop : drops) {
            if(!drop.isFalling) {
                continue;
            }
            Vector2 move = new Vector2(-0.5f, -1).nor();
            drop.position.add(move.scl(speed * delta));
        }

        for (int i = drops.size - 1; i >= 0; i--) {
            if(drops.get(i).position.y <= -drops.get(i).region.getRegionHeight() * scale) {
                drops.removeIndex(i);
            }
        }

        for (Drop drop : drops) {
            if(drop.position.y - drop.targetY <= 0.1) {
                drop.isFalling = false;
            }
        }

        for (int i = drops.size - 1; i >= 0; i--) {
            Drop drop = drops.get(i);
            if(drop.isFalling) {
                continue;
            }
            if(drop.animation.isAnimationFinished(drop.stateTime)) {
                drops.removeIndex(i);
            }
            drop.region = drop.animation.getKeyFrame(drop.stateTime);
            drop.stateTime += delta;
        }
    }

    private static class Drop {
        Vector2 position;
        int targetY;
        TextureRegion region;
        boolean isFalling;
        float stateTime = 0;
        Animation<TextureRegion> animation;

        public Drop(Vector2 position, int targetY,
                    TextureRegion region, boolean isFalling,
                    Animation<TextureRegion> animation) {

            this.position = position;
            this.targetY = targetY;
            this.region = region;
            this.isFalling = isFalling;
            this.animation = animation;
        }
    };
}
