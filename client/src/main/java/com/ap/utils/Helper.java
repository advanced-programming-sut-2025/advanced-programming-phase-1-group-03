package com.ap.utils;

import com.ap.asset.AssetService;
import com.ap.asset.MusicAsset;
import com.ap.asset.TilesetAsset;
import com.ap.audio.AudioService;
import com.ap.component.Container;
import com.ap.component.Physic;
import com.ap.component.Transform;
import com.ap.items.Item;
import com.ap.model.Season;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Random;

public class Helper {


    public static TextureRegion createWhiteTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        var texture = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        return texture;
    }

    public static void removeEntity(Entity entity, Engine engine, World world) {
        if(Container.mapper.has(entity)) {
            for(Entity child : Container.mapper.get(entity).getChildren()) {
                removeEntity(child, engine, world);
            }
        }
        if(Physic.mapper.has(entity)) {
            Physic physic = Physic.mapper.get(entity);
            world.destroyBody(physic.getBody());
        }
        engine.removeEntity(entity);
    }
    public static int random(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }


    public static float calculateDistance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }


    public static void changeSeasonTileset(Season season, TiledMap currentMap, AssetService assetService) {
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

    private static void updateAnimatedTile(AnimatedTiledMapTile animatedTile,
                                    Texture newTileSheet, int tileWidth, int tileHeight, int offset) {
        for(StaticTiledMapTile frame : animatedTile.getFrameTiles()) {
            int regionX = ((frame.getId()-offset) % (newTileSheet.getWidth() / tileWidth)) * tileWidth;
            int regionY = ((frame.getId()-offset) / (newTileSheet.getWidth() / tileWidth)) * tileHeight;
            TextureRegion newRegion = new TextureRegion(newTileSheet, regionX, regionY, tileWidth, tileHeight);
            frame.setTextureRegion(newRegion);
        }
    }
}
