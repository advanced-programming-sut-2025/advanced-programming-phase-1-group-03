package com.ap.tiled;

import com.ap.asset.AssetService;
import com.ap.component.*;
import com.ap.items.EntityFactory;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class TiledMapGenerator {
    private final int treeCount = 35;
    private final int stoneCount = 70;
    private final int grassCount = 200;
    private final int woodCount = 50;

    private final float minimumDistanceBetweenTrees = 5f;

    private final Engine engine;
    private final AssetService assetService;
    private final World world;

    public TiledMapGenerator(Engine engine, AssetService assetService, World world) {
        this.engine = engine;
        this.assetService = assetService;
        this.world = world;
    }

    public void generate(TiledMap map) {
        generateTrees(map);
        generateStones(map);
        generateGrasses(map);
        generateWoods(map);
    }

    private void generateWoods(TiledMap map) {
        Array<Vector2> positions = new Array<>();

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);

        for(int i = 0; i < woodCount; i++) {
            while(true) {
                int x = new Random().nextInt(10, mapWidth - 10);
                int y = new Random().nextInt(10, mapHeight - 10);
                boolean accepted = isTileValid(map, x, y);
                if (isPositionHasMinimumDistance(positions, x, y, accepted, 1)) break;
            }
        }
        for(Vector2 position : positions) {
            Entity entity = EntityFactory.CreateWoodEntity(position, assetService, world);
            Helper.addEntity(entity, engine);
        }
    }

    private void generateGrasses(TiledMap map) {
        Array<Vector2> positions = new Array<>();

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);

        for(int i = 0; i < grassCount; i++) {
            while(true) {
                int x = new Random().nextInt(10, mapWidth - 10);
                int y = new Random().nextInt(10, mapHeight - 10);
                boolean accepted = isTileValidForGrass(map, x, y);
                if (isPositionHasMinimumDistance(positions, x, y, accepted, 1)) break;
            }
        }
        for(Vector2 position : positions) {
            int type = new Random().nextInt(3);
            Entity entity = EntityFactory.CreateGrassEntity(position, type, assetService, world);
            engine.addEntity(entity);
        }
    }

    private boolean isPositionHasMinimumDistance(Array<Vector2> positions, int x, int y, boolean accepted, float minDistance) {
        for(Vector2 position : positions) {
            Vector2 dis = position.cpy().sub(new Vector2(x, y));
            if(dis.len() < minDistance) {
                accepted = false;
            }
        }
        accepted &= isTileCollied(x, y);
        if(accepted) {
            positions.add(new Vector2(x, y));
            return true;
        }
        return false;
    }


    private void generateStones(TiledMap map) {
        Array<Vector2> positions = new Array<>();

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);

        for(int i = 0; i < stoneCount; i++) {
            while(true) {
                int x = new Random().nextInt(10, mapWidth - 10);
                int y = new Random().nextInt(10, mapHeight - 10);
                boolean accepted = isTileValid(map, x, y);
                if (isPositionHasMinimumDistance(positions, x, y, accepted, 1)) break;
            }
        }
        for(Vector2 position : positions) {
            int type = new Random().nextInt(2);
            Entity entity = EntityFactory.CreateStoneEntity(position, type, assetService, world);
            engine.addEntity(entity);
        }
    }

    private boolean isTileCollied(int x, int y) {
        for(Entity entity : engine.getEntities()) {
            if(!Transform.mapper.has(entity)) {
                continue;
            }
            Transform transform = Transform.mapper.get(entity);
            Rectangle entityRect = new Rectangle(
                    transform.getPosition().x,
                    transform.getPosition().y,
                    transform.getSize().x,
                    transform.getSize().y);
            Rectangle stoneRect = new Rectangle(x, y, 1, 1);
            if(entityRect.overlaps(stoneRect)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTileValid(TiledMap map, int x, int y) {
        for(MapLayer layer : map.getLayers()) {
            if(layer instanceof TiledMapTileLayer tileLayer) {
                var cell = tileLayer.getCell(x, y);
                if(cell == null) continue;
                var tile = cell.getTile();
                if(tile.getProperties().get("Type", "", String.class).equals("Dirt")) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean isTileValidForGrass(TiledMap map, int x, int y) {
        for(MapLayer layer : map.getLayers()) {
            if(layer instanceof TiledMapTileLayer tileLayer) {
                var cell = tileLayer.getCell(x, y);
                if(cell == null) continue;
                var tile = cell.getTile();
                String type = tile.getProperties().get("Type", "", String.class);
                if(type.equals("Dirt") || type.equals("Grass")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void generateTrees(TiledMap map) {
        Array<Vector2> positions = new Array<>();

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);

        for(int i = 0; i < treeCount; i++) {
            while(true) {
                int x = new Random().nextInt(10, mapWidth - 10);
                int y = new Random().nextInt(10, mapHeight - 10);
                boolean accepted = isTileValid(map, x, y);;
                if (isPositionHasMinimumDistance(positions, x, y, accepted, minimumDistanceBetweenTrees)) break;
            }
        }

        for(Vector2 position : positions) {
            String name = Trees.values()[new Random().nextInt(Trees.values().length)].name();
            Entity entity = EntityFactory.CreateTreeEntity(position, name, assetService, world);
            engine.addEntity(entity);
            for(Entity child : Container.mapper.get(entity).getChildren()) {
                engine.addEntity(child);
            }
        }
    }


    private enum Trees {
        oak,
    }
}
