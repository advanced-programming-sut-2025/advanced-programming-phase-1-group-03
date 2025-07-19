package com.ap.system;

import com.ap.Constraints;
import com.ap.component.Graphic;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderSystem extends SortedIteratingSystem {
    private BatchTiledMapRenderer renderer;
    private Batch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    private List<MapLayer> fgdLayers, bgdLayers;

    public RenderSystem(Batch batch, Viewport viewport, OrthographicCamera camera) {
        super(Family.all(Transform.class, Graphic.class).get(),
                Comparator.comparing(Transform.mapper::get));

        renderer = new OrthogonalTiledMapRenderer(null, Constraints.UNIT_SCALE, batch);
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;

        fgdLayers = new ArrayList<>();
        bgdLayers = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime) {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        viewport.apply();

        batch.begin();
        batch.setColor(Color.WHITE);
        renderer.setView(camera);

        bgdLayers.forEach(this::renderMapLayer);

        forceSort();
        super.update(deltaTime);

        batch.setColor(Color.WHITE);
        fgdLayers.forEach(this::renderMapLayer);
        batch.end();
    }
    void renderMapLayer (MapLayer layer) {
        if (!layer.isVisible()) return;
        if (layer instanceof MapGroupLayer) {
            MapLayers childLayers = ((MapGroupLayer)layer).getLayers();
            for (int i = 0; i < childLayers.size(); i++) {
                MapLayer childLayer = childLayers.get(i);
                if (!childLayer.isVisible()) continue;
                renderMapLayer(childLayer);
            }
        } else {
            if (layer instanceof TiledMapTileLayer) {
                renderer.renderTileLayer((TiledMapTileLayer)layer);
            } else if (layer instanceof TiledMapImageLayer) {
                renderer.renderImageLayer((TiledMapImageLayer)layer);
            } else {
                renderer.renderObjects(layer);
            }
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.mapper.get(entity);
        Graphic graphic = Graphic.mapper.get(entity);
        if(graphic.getTexture() == null)
            return;

        var position = transform.getPosition();
        var size = transform.getSize();
        var scaling = transform.getScaling();

        batch.setColor(graphic.getColor());
        batch.draw(
                graphic.getTexture(),
                position.x, position.y,
                size.x * 0.5f, size.y * 0.5f,
                size.x, size.y,
                scaling.x, scaling.y,
                transform.getRotationDeg()
        );
    }

    public void setMap(TiledMap tiledMap) {
        renderer.setMap(tiledMap);

        fgdLayers.clear();
        bgdLayers.clear();
        List<MapLayer> currentLayers = bgdLayers;
        for (MapLayer layer : tiledMap.getLayers()) {
            if ("objects".equals(layer.getName())) {
                currentLayers = fgdLayers;
                continue;
            }
            if (layer.getClass().equals(MapLayer.class)) {
                continue;
            }
            currentLayers.add(layer);
        }
    }
}
