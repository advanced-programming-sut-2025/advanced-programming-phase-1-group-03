package com.ap.ui.effects;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.CameraFollow;
import com.ap.component.Player;
import com.ap.system.CameraSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Snowfall extends Actor {

    private final Array<TextureRegion> regions;
    private final int framesNumber;
    private final int column;
    private final float tileHeight = 32;
    private final float tileWidth = 32;
    private Integer[] frameIndex;
    private float timer = 0f;
    private float animationSpeed;
    private final float constantAnimationSpeed = 1 / 10f;

    private Engine engine;
    private Entity playerEntity = null;
    private CameraSystem cameraSystem = null;

    public Snowfall(AssetService assetService, Engine engine) {
        TextureAtlas atlas = assetService.get(AtlasAsset.Snowfall);
        regions = new Array<>(atlas.findRegions("snow"));
        framesNumber = regions.size;
        column = (int) Math.ceil((float) Constraints.WORLD_WIDTH_RESOLUTION / tileWidth);
        frameIndex = new Integer[column];
        setAnimations();
        this.engine = engine;
    }

    private void setOtherSystemsAndEntities() {
        if (playerEntity == null) {
            playerEntity = engine.getEntitiesFor(Family.all(Player.class).get()).first();
        }
        if (cameraSystem == null) {
            cameraSystem = engine.getSystem(CameraSystem.class);
        }
    }

    public void setAnimations() {
        Random random = new Random();
        for (int i = 0; i < column; i++) {
            frameIndex[i] = random.nextInt(framesNumber);
        }
        animationSpeed = constantAnimationSpeed;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setOtherSystemsAndEntities();
        timer += delta;
//        Vector2 cameraVelocity = cameraSystem.getMovingVelocity();
//        animationSpeed = constantAnimationSpeed - cameraVelocity.y;
//        animationSpeed = MathUtils.clamp(animationSpeed, 0, 0.2f);
//        if (cameraVelocity.y > 0.01f) animationSpeed /= 4f;
//        if (cameraVelocity.y < -0.01f) animationSpeed *= 4f;
        if (animationSpeed > 0) while (timer >= animationSpeed) {
            timer -= animationSpeed;
            for (int i = 0; i < column; i++) {
                ++frameIndex[i];
                frameIndex[i] %= framesNumber;
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setOtherSystemsAndEntities();
        float x;
        float y = Constraints.WORLD_HEIGHT_RESOLUTION + cameraSystem.getMovingVelocity().y;
        while (y > 0) {
            x = 0f;
            for (int i = 0; i < column; i++) {
                batch.draw(regions.get(frameIndex[i]), x, y, tileWidth, tileHeight);
                x += tileWidth;
            }
            y -= tileHeight;
        }
    }

}
