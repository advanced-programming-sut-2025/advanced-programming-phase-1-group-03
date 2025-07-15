package com.ap.system;

import com.ap.Constraints;
import com.ap.component.CameraFollow;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraSystem extends IteratingSystem {
    private final Camera camera;
    private final Vector2 targetPosition = new Vector2();

    private final float smoothingFactor = Constraints.CAMERA_SMOOTHING_FACTOR;
    private float mapW, mapH;

    public CameraSystem(Camera camera) {
        super(Family.all(Transform.class, CameraFollow.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.mapper.get(entity);
        calculateTargetPosition(transform.getPosition());

        float progress = smoothingFactor * deltaTime;

        float smoothedX = MathUtils.lerp(camera.position.x, targetPosition.x, progress);
        float smoothedY = MathUtils.lerp(camera.position.y, targetPosition.y, progress);
        camera.position.set(smoothedX, smoothedY, camera.position.z);
    }

    private void calculateTargetPosition(Vector2 playerPosition) {
        float targetX = playerPosition.x;
        float targetY = playerPosition.y;

        float camHalfW = camera.viewportWidth * 0.5f;
        if(mapW > camHalfW) {
            float min = Math.min(camHalfW, mapW - camHalfW);
            float max = Math.max(camHalfW, mapW - camHalfW);

            // prevent camera to show outside the map
            targetX = MathUtils.clamp(targetX, min, max);
        }

        float camHalfH = camera.viewportHeight * 0.5f;
        if(mapH > camHalfH) {
            float min = Math.min(camHalfH, mapH - camHalfH);
            float max = Math.max(camHalfH, mapH - camHalfH);

            // prevent camera to show outside the map
            targetY = MathUtils.clamp(targetY, min, max);
        }
        targetPosition.set(targetX, targetY);
    }

    public void setMap(TiledMap map) {
        int width = map.getProperties().get("width", Integer.class);
        int height = map.getProperties().get("height", Integer.class);
        int tileW = map.getProperties().get("tilewidth", Integer.class);
        int tileH = map.getProperties().get("tileheight", Integer.class);

        // usually "tileW * Constraints.UNIT_SCALE" is 1
        mapW = width * tileW * Constraints.UNIT_SCALE;
        mapH = height * tileH * Constraints.UNIT_SCALE;

        Entity player = getEntities().first();
        if(player == null)
            return;

        Transform playerTransform = Transform.mapper.get(player);
        calculateTargetPosition(playerTransform.getPosition());

        camera.position.set(targetPosition, camera.position.z);
    }
}
