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
                if (spawnMineral()) break;
            }
        }
    }
    public void spawnMinerals() {
        for (int i = 0; i < Constraints.MINERAL_PER_DAY; i++) {
            spawnMineral();
        }
    }

    private boolean spawnMineral() {
        int x = Helper.random(0, mapWidth);
        int y = Helper.random(0, mapHeight);
        boolean ok = true;
        boolean hasMineralSpawner = false;

        for (MapLayer layer : map.getLayers()) {
            if (layer instanceof TiledMapTileLayer tileLayer) {
                var cell = tileLayer.getCell(x, y);
                if (cell == null) continue;
                var tile = cell.getTile();
                if (!tile.getProperties().get("mineralSpawner", false, Boolean.class)) {
                    ok = false;
                } else {
                    hasMineralSpawner = true;
                }
            }
        }
        ok &= hasMineralSpawner;
        if(Helper.getTopBodyAtPoint(new Vector2(x, y), world, map).getUserData() instanceof Entity) {
            ok = false;
        }
        if(!ok) {
            return false;
        }
        Entity entity = EntityFactory.instance.CreateMineralNodeEntity(
                new Vector2(x, y),
                MineralNodes.getRandom(),
                world);
        engine.addEntity(entity);
        return true;
    }


}
