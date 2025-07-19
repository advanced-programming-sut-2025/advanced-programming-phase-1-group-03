package com.ap.tiled;

import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.TilesetAsset;
import com.ap.model.Season;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;

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

        changeSeasonTileset(Season.Winter);
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

    @FunctionalInterface
    public interface LoadTileConsumer {
        void accept(TiledMapTile tile, int x, int y);
    }
}
