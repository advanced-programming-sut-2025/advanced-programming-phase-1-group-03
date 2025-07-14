package com.ap.tiled;

import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.function.Consumer;

public class TiledService {
    private final AssetService assetService;
    private TiledMap currentMap = null;

    // We use this consumer to notify other systems when map changes
    private Consumer<TiledMap> mapChangeConsumer;
    private LoadTileConsumer loadTileConsumer;
    private Consumer<TiledMapTileMapObject> loadObjectConsumer;

    public TiledService(AssetService assetService) {
        this.assetService = assetService;
    }

    public TiledMap load(MapAsset asset) {
        TiledMap map = assetService.load(asset);
        // Add to map index to its properties, we use it to unload map in setMap function
        map.getProperties().put("mapAsset", asset);
        return map;
    }

    public void setMap(TiledMap startMap) {
        if(currentMap != null) {
            // Unload the map, because we don't want store big data in out RAM
            assetService.unload(startMap.getProperties().get("mapAsset", MapAsset.class));
        }
        this.currentMap = startMap;

        loadMapObjects(startMap);

        if(mapChangeConsumer != null) {
            // notify All
            mapChangeConsumer.accept(startMap);
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
            } else {
                throw new GdxRuntimeException("Unsupported map object: " + object.getClass().getName());
            }
        }
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

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer) {
        this.mapChangeConsumer = mapChangeConsumer;
    }

    public void setLoadTileConsumer(LoadTileConsumer loadTileConsumer) {
        this.loadTileConsumer = loadTileConsumer;
    }

    public void setLoadObjectConsumer(Consumer<TiledMapTileMapObject> loadObjectConsumer) {
        this.loadObjectConsumer = loadObjectConsumer;
    }

    @FunctionalInterface
    public interface LoadTileConsumer {
        void accept(TiledMapTile tile, int x, int y);
    }
}
