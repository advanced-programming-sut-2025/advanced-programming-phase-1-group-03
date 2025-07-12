package com.ap.system;

import com.ap.Constraints;
import com.ap.component.Graphic;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Comparator;

public class RenderSystem extends IteratingSystem {
    private TiledMap currentMap;
    private BatchTiledMapRenderer renderer;
    private Batch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    public RenderSystem(Batch batch, Viewport viewport, OrthographicCamera camera) {
        super(Family.all(Transform.class, Graphic.class).get());
        renderer = new OrthogonalTiledMapRenderer(null, Constraints.UNIT_SCALE, batch);
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        renderer.setView(camera);
        viewport.apply();

        renderer.render();

        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void setMap(TiledMap tiledMap) {
        currentMap = tiledMap;
        renderer.setMap(currentMap);
    }
}
