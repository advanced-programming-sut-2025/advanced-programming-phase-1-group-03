package com.ap.tiled;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.*;
import com.ap.items.ItemFactory;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class TiledMapGenerator {
    private final int treeCount = 20;
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
    }

    private void generateTrees(TiledMap map) {
        Array<Vector2> positions = new Array<>();

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);

        for(int i = 0; i < treeCount; i++) {
            while(true) {
                int x = new Random().nextInt(10, mapWidth - 10);
                int y = new Random().nextInt(10, mapHeight - 10);
                boolean accepted = false;
                for(MapLayer layer : map.getLayers()) {
                    if(layer instanceof TiledMapTileLayer tileLayer) {
                        var cell = tileLayer.getCell(x, y);
                        if(cell == null) continue;
                        var tile = cell.getTile();
                        if(tile.getProperties().get("Type", "", String.class).equals("Dirt")) {
                            accepted = true;
                        }
                    }
                }
                for(Vector2 position : positions) {
                    Vector2 dis = position.cpy().sub(new Vector2(x, y));
                    if(dis.len() < minimumDistanceBetweenTrees) {
                        accepted = false;
                    }
                }
                if(accepted) {
                    positions.add(new Vector2(x, y));
                    break;
                }
            }
        }

        for(Vector2 position : positions) {
            String name = Trees.values()[new Random().nextInt(Trees.values().length)].name();
            Entity entity = ItemFactory.CreateTreeEntity(position, name, assetService, world);
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
