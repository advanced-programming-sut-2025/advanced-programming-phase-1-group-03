package com.ap.utils;

import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.component.*;
import com.ap.items.Item;
import com.ap.managers.PlayerManager;
import com.ap.model.Season;
import com.ap.model.ServerPlayer;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class Helper {
    private static final Set<Integer> entities = new HashSet<Integer>();
    private static final Map<Engine, Integer> engineCache = new HashMap<Engine, Integer>();

    public static void addEntity(Entity entity, Engine engine) {
        int id;
        do {
            id = new Random().nextInt(10000000);
        }
        while(entities.contains(id));


        // Add network component to network entities
        if(Graphic.mapper.has(entity) && Transform.mapper.has(entity) && !Network.mapper.has(entity)) {
            entity.add(new Network(id));
            entities.add(id);
        }

        engine.addEntity(entity);
        if(Container.mapper.has(entity)) {
            for(Entity child : Container.mapper.get(entity).getChildren()) {
                addEntity(child, engine);
            }
        }
    }
    public static float calculateDistance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public static boolean canPlace(Vector2 position, Vector2 size, World world) {
        final boolean[] found = {false};
        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if(!fixture.isSensor())
                    found[0] = true;
                return true;
            }
        }, position.x, position.y , position.x + size.x, position.y + size.y);
        return !found[0];
    }


    public static int random(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static float floatRandom(float min, float max) {
        return new Random().nextFloat() * (max - min) + min;
    }
    public static int getEngineId(Engine engine) {
        return engineCache.get(engine);
    }

    public static int createIdForEngine(Engine engine) {
        if(!engineCache.containsKey(engine)) {
            int id;
            do {
                id = new Random().nextInt(10000000);
            }while(entities.contains(id));
            engineCache.put(engine, id);
            return id;
        } else {
            return engineCache.get(engine);
        }
    }


    public static void playMusicOfSeason(Array<ServerPlayer> players, Season season) {
        for(ServerPlayer player : players) {
            var audioService = player.playerManager.getAudioService();
            switch (season) {
                case Spring -> {
                    audioService.playMusic(MusicAsset.Spring);
                }
                case Summer -> {
                    audioService.playMusic(MusicAsset.Summer);
                }
                case Fall -> {
                    audioService.playMusic(MusicAsset.Fall);
                }
                case Winter -> {
                    audioService.playMusic(MusicAsset.Winter);
                }
            }
        }

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

    public static Item.WorldObject getTopBodyAtPoint(Vector2 tilePos, World world, TiledMap map) {

        tilePos.add(new Vector2(0.5f, 0.5f));
        final Body[] topBody = {null};
        final int[] highestZIndex = {-1};
        final float[] highestYIndex = {-1};
        int x = (int) tilePos.x, y = (int) tilePos.y;

        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if(fixture.getBody().getUserData() instanceof Entity entity) {
                    if (Transform.mapper.has(entity)) {
                        Transform transform = Transform.mapper.get(entity);
                        if (transform.getZ() > highestZIndex[0]) {
                            highestZIndex[0] = transform.getZ();
                            highestYIndex[0] = -1;
                            topBody[0] = fixture.getBody();
                        } else if(transform.getZ() == highestZIndex[0] &&
                                transform.getSortOffsetY()  > highestYIndex[0]) {
                            highestYIndex[0] = transform.getSortOffsetY();
                            topBody[0] = fixture.getBody();
                        }
                    }
                }
                return true;
            }
        }, tilePos.x - 0.01f, tilePos.y - 0.01f, tilePos.x + 0.01f, tilePos.y + 0.01f);

        if(highestZIndex[0] == -1) {
            TiledMapTile last = null;

            for(MapLayer layer : map.getLayers()) {
                if(!(layer instanceof TiledMapTileLayer tileLayer)) {
                    continue;
                }
                var cell = (tileLayer).getCell((int) tilePos.x, (int) tilePos.y);
                if(cell == null) {
                    continue;
                }
                last = cell.getTile();
            }
            return new Item.WorldObject(last, tilePos);
        }
        return new Item.WorldObject(topBody[0].getUserData(), topBody[0].getPosition());
    }

    public static void sendToAll(Array<ServerPlayer> players, Object message) {
        for(ServerPlayer serverPlayer : players) {
            serverPlayer.connection.sendUDP(message);
        }
    }
    public static void sendToAllTCP(Array<ServerPlayer> players, Object message) {
        for(ServerPlayer serverPlayer : players) {
            serverPlayer.connection.sendTCP(message);
        }
    }

    public static Entity getPlayer(Engine engine, int id) {
        for(Entity entity : engine.getEntitiesFor(Family.all(Player.class).get())) {
            if(Player.mapper.get(entity).id == id) {
                return entity;
            }
        }
        return null;
    }

    public static ServerPlayer findPlayer(Array<ServerPlayer> players, int id) {
        for(ServerPlayer serverPlayer : players) {
            if(serverPlayer.id == id) {
                return serverPlayer;
            }
        }
        throw new NullPointerException("Player not found");
    }
}
