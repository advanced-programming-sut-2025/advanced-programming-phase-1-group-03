package com.ap.tiled;

import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.function.Consumer;

public class TiledService {
    private final AssetService assetService;
    private TiledMap currentMap = null;

    // We use this consumer to notify other systems when map changes
    private Consumer<TiledMap> mapChangeConsumer;


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
        if(mapChangeConsumer != null) {
            // notify All
            mapChangeConsumer.accept(startMap);
        }
    }

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer) {
        this.mapChangeConsumer = mapChangeConsumer;
    }
}
