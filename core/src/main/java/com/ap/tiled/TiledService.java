package com.ap.tiled;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.TilesetAsset;
import com.ap.model.Season;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;

import java.util.HashMap;
import java.util.function.Consumer;

public class TiledService {
    private final AssetService assetService;
    private TiledMap currentMap = null;

    private HashMap<String, Vector2> spawnPoints = new HashMap<>();

    // We use this consumer to notify other systems when map changes
    private Consumer<TiledMap> mapChangeConsumer;
    private LoadTileConsumer loadTileConsumer;
    private Consumer<TiledMapTileMapObject> loadObjectConsumer;
    private Consumer<MapObject> loadBoundaryConsumer;
    private Consumer<TiledMap> generateItemsConsumer;
    private Consumer<MapObject> loadSpanwerConsumer;

    public TiledService(AssetService assetService) {
        this.assetService = assetService;
    }

    public TiledMap load(MapAsset asset) {
        TiledMap map = assetService.load(asset);
        return map;
    }

    public void setMap(TiledMap startMap) {
        this.currentMap = startMap;

        loadMapObjects(startMap);

        if(mapChangeConsumer != null) {
            // notify All
            mapChangeConsumer.accept(startMap);
        }
        if(startMap.getProperties().get("generateItems", false, Boolean.class)) {
            generateItemsConsumer.accept(startMap);
        }
    }

    private void loadMapObjects(TiledMap startMap) {
        for(MapLayer layer : startMap.getLayers()) {
            if(layer instanceof TiledMapTileLayer tileLayer) {
                loadTileLayer(tileLayer);
            } else if("objects".equals(layer.getName())) {
                loadObjectLayer(layer);
            } else {
                throw new GdxRuntimeException("Unsupported map layer: " + layer.getName());
            }
        }
    }

    private void loadObjectLayer(MapLayer layer) {
        for(MapObject object : layer.getObjects()) {
            if(object instanceof TiledMapTileMapObject tiledMapObject) {
                loadObjectConsumer.accept(tiledMapObject);
            } else if(object instanceof PolygonMapObject polygon) {
                loadBoundaryConsumer.accept(polygon);
            } else if(object instanceof RectangleMapObject point) {
                setSpawnPoint(point);
                setSpawner(point);
            }else {
                throw new GdxRuntimeException("Unsupported map object: " + object.getClass().getName());
            }
        }
    }

    private void setSpawner(RectangleMapObject point) {
        String location = point.getProperties().get("spawner", "", String.class);
        if(location.isBlank()) {
            return;
        }
        loadSpanwerConsumer.accept(point);
    }

    private void setSpawnPoint(RectangleMapObject point) {
        String location = point.getProperties().get("StartLocation", "", String.class);
        if(location.isBlank()) {
            return;
        }
        Vector2 pos = new Vector2(point.getRectangle().getX(), point.getRectangle().getY());
        pos.scl(Constraints.UNIT_SCALE);
        spawnPoints.put(location, pos);
    }

    private void loadTileLayer(TiledMapTileLayer tileLayer) {
        for(int x = 0; x < tileLayer.getWidth(); x++) {
            for(int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if(cell == null)
                    continue;
                loadTileConsumer.accept(cell.getTile(), x, y);
            }
        }
    }

    public void changeSeasonTileset(Season season) {
        if(currentMap == null) {
            return;
        }

        for(TiledMapTileSet tileset : currentMap.getTileSets()) {
            boolean seasonal = tileset.getProperties().get("seasonal", false, Boolean.class);
            if(!seasonal) {
                continue;
            }

            String textureName = tileset.getName() + season.name();
            Texture newTileSheet = assetService.get(TilesetAsset.valueOf(textureName));

            if(newTileSheet == null) {
                throw new GdxRuntimeException("Texture not found");
            }
            int offset = tileset.getProperties().get("firstgid", int.class);

            for(int i = 0; i < tileset.size(); i++) {
                int tileId = i+offset;

                TiledMapTile tile = tileset.getTile(tileId);

                if(tile == null)
                    continue;

                int tileWidth = tileset.getProperties().get("tilewidth", Integer.class);
                int tileHeight = tileset.getProperties().get("tileheight", Integer.class);

                if(tile instanceof AnimatedTiledMapTile animatedTile) {
                    updateAnimatedTile(animatedTile, newTileSheet, tileWidth, tileHeight, offset);
                    continue;
                }

                int regionX = (i % (newTileSheet.getWidth() / tileWidth)) * tileWidth;
                int regionY = (i / (newTileSheet.getWidth() / tileWidth)) * tileHeight;

                TextureRegion newRegion = new TextureRegion(newTileSheet, regionX, regionY, tileWidth, tileHeight);
                tile.setTextureRegion(newRegion);
            }
        }
    }

    private void updateAnimatedTile(AnimatedTiledMapTile animatedTile,
                                    Texture newTileSheet, int tileWidth, int tileHeight, int offset) {
        for(StaticTiledMapTile frame : animatedTile.getFrameTiles()) {
            int regionX = ((frame.getId()-offset) % (newTileSheet.getWidth() / tileWidth)) * tileWidth;
            int regionY = ((frame.getId()-offset) / (newTileSheet.getWidth() / tileWidth)) * tileHeight;
            TextureRegion newRegion = new TextureRegion(newTileSheet, regionX, regionY, tileWidth, tileHeight);
            frame.setTextureRegion(newRegion);
        }
    }

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer) {
        this.mapChangeConsumer = mapChangeConsumer;
    }

    public void setLoadTileConsumer(LoadTileConsumer loadTileConsumer) {
        this.loadTileConsumer = loadTileConsumer;
    }

    public void setLoadObjectConsumer(Consumer<TiledMapTileMapObject> loadObjectConsumer) {
        this.loadObjectConsumer = loadObjectConsumer;
    }

    public void setBoundaryConsumer(Consumer<MapObject> loadBoundaryConsumer) {
        this.loadBoundaryConsumer = loadBoundaryConsumer;
    }

    public void setGenerateItemsConsumer(Consumer<TiledMap> generateItemsConsumer) {
        this.generateItemsConsumer = generateItemsConsumer;
    }

    public void setLoadSpanwerConsumer(Consumer<MapObject> loadSpanwerConsumer) {
        this.loadSpanwerConsumer = loadSpanwerConsumer;
    }

    @FunctionalInterface
    public interface LoadTileConsumer {
        void accept(TiledMapTile tile, int x, int y);
    }
}
