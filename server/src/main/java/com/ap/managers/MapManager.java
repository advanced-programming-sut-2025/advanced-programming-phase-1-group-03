package com.ap.managers;

import com.ap.asset.MapAsset;
import com.ap.maps.Farm;
import com.ap.maps.IMap;
import com.ap.model.GameManager;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.ChangeMapNotifier;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapManager {
    ConcurrentHashMap<MapKey, IMap> mapCache = new ConcurrentHashMap<>();

    public Map<ServerPlayer, IMap> currentMaps = new HashMap<>();
    Map<ServerPlayer, MapAsset> currentMapAssets = new HashMap<>();
    Map<ServerPlayer, MapAsset> farmMaps = new HashMap<>();

    private final ArrayList<ServerPlayer> players = new ArrayList<>();
    private final GameManager gameManager;

    public MapManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void playerArrived(ServerPlayer serverPlayer, MapAsset playerMap, PlayerManager playerManager) {
        players.add(serverPlayer);
        for(MapAsset mapAsset : MapAsset.values()) {
            ServerPlayer player = mapAsset.isMapPublic ? null : serverPlayer;

            IMap map;
            if(!mapCache.containsKey(new MapKey(mapAsset, player))) {
                map = createMap(mapAsset, playerManager, serverPlayer.id);
                map.setup(mapAsset);
            } else {
                map = mapCache.get(new MapKey(mapAsset, player));
            }
            map.addPlayer(serverPlayer, mapAsset);
            mapCache.put(new MapKey(mapAsset, player), map);
        }
        farmMaps.put(serverPlayer, playerMap);

        setMap(serverPlayer, playerMap);
    }

    private IMap createMap(MapAsset map, PlayerManager playerManager, int playerId) {
        switch (map) {
            case Farm1, Farm2, Forest, Town -> {
                return new Farm(gameManager, playerManager, this, playerId);
            } case House,Greenhouse -> {
                return new Farm(gameManager, playerManager, this, playerId);
                //return new House(game, gameScreen);
            } case Mine -> {
                return new Farm(gameManager, playerManager, this, playerId);
                //return new Mine(game, gameScreen);
            } case StardropSaloon, CarpenterShop -> {
                return new Farm(gameManager, playerManager, this, playerId);
                // return new Store(game, gameScreen);
            }
        }
        throw new IllegalArgumentException("Map " + map.name() + " is not supported");
    }

    public void update(float delta) {
        for(IMap map : mapCache.values()) {
            map.update(delta);
        }
    }

    public void setMap(int id, MapAsset mapAsset) {
        var player = players.stream().filter((p) -> p.id == id).findFirst().orElse(null);
        setMap(player, mapAsset);
    }

    public void setMap(ServerPlayer player, MapAsset mapAsset) {
        assert player != null;

        var currentMap = currentMaps.get(player);
        Entity playerEntity = null;
        if(currentMap != null) {
            playerEntity = currentMap.leave(player);
        }
        IMap newMap;
        if(mapCache.containsKey(new MapKey(mapAsset, player))) {
            newMap = mapCache.get(new MapKey(mapAsset, player));
        } else {
            newMap = mapCache.get(new MapKey(mapAsset, null));
        }
        currentMaps.put(player, newMap);
        currentMapAssets.put(player, mapAsset);

        newMap.load(playerEntity);
        player.connection.sendTCP(new ChangeMapNotifier(newMap.getEngineId()));
    }

    private record MapKey(MapAsset mapAsset, ServerPlayer belongingPlayer) {
    }
}
