package com.ap.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicDebugRenderSystem extends EntitySystem {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Camera camera;

    public PhysicDebugRenderSystem(Camera camera, World world) {
        this.camera = camera;
        this.world = world;
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float deltaTime) {
        this.debugRenderer.render(world, camera.combined);
    }
}
