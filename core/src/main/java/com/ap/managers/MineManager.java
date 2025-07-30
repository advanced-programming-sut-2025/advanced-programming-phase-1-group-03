package com.ap.managers;

import com.ap.Constraints;
import com.ap.items.EntityFactory;
import com.ap.model.MineralNodes;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class MineManager {
    private final Engine engine;
    private final int mapWidth, mapHeight;
    private final World world;
    private final TiledMap map;

    public MineManager(Engine engine, TiledMap map, World world) {
        this.engine = engine;
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        this.map = map;
        this.world = world;
        spawnDefaultMinerals();
    }
    private void spawnDefaultMinerals() {
        for(int i = 0; i < Constraints.DEFAULT_MINERALS_COUNT; i++){
            while(true) {
                int x = Helper.random(0, mapWidth);
                int y = Helper.random(0, mapHeight);
                boolean ok = true;

                for (MapLayer layer : map.getLayers()) {
                    if (layer instanceof TiledMapTileLayer tileLayer) {
                        var cell = tileLayer.getCell(x, y);
                        if (cell == null) continue;
                        var tile = cell.getTile();
                        if (!tile.getProperties().get("mineralSpawner", false, Boolean.class)) {
                            ok = false;
                        }
                    }
                }
                if(!ok) {
                    continue;
                }

                Entity entity = EntityFactory.instance.CreateMineralNodeEntity(
                        new Vector2(x, y),
                        MineralNodes.getRandom(),
                        world);
                engine.addEntity(entity);
                break;
            }
        }
    }
    public void spawnMinerals() {

    }
}
