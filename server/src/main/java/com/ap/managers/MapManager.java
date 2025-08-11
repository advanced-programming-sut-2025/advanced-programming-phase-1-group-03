package com.ap.managers;

import com.ap.asset.MapAsset;
import com.ap.maps.Farm;
import com.ap.maps.IMap;
import com.ap.model.GameManager;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.ChangeMapNotifier;

import java.util.HashMap;

public class MapManager {
    HashMap<MapAsset, IMap> mapCache = new HashMap<>();
    IMap currentMap = null;
    MapAsset currentMapAsset;

    private final PlayerManager playerManager;
    private final ServerPlayer player;
    private final MapAsset farmMap;
    private final GameManager gameManager;

    public MapManager(GameManager gameManager, PlayerManager playerManager, MapAsset map, ServerPlayer player) {
        this.gameManager = gameManager;
        this.playerManager = playerManager;
        this.player = player;
        this.farmMap = map;
        loadAllMaps();
        setMap(map);
    }

    public void loadAllMaps() {
        for(MapAsset mapAsset : MapAsset.values()) {
            IMap map = createMap(mapAsset);
            mapCache.put(mapAsset, map);
            map.setup(mapAsset);
        }
    }
    private IMap createMap(MapAsset map) {
        switch (map) {
            case Farm1, Farm2, Forest, Town -> {
                return new Farm(gameManager, playerManager, player, this);
            } case House,Greenhouse -> {
                return new Farm(gameManager, playerManager, player, this);
                //return new House(game, gameScreen);
            } case Mine -> {
                return new Farm(gameManager, playerManager, player, this);
                //return new Mine(game, gameScreen);
            } case StardropSaloon, CarpenterShop -> {
                return new Farm(gameManager, playerManager, player, this);
                // return new Store(game, gameScreen);
            }
        }
        throw new IllegalArgumentException("Map " + map.name() + " is not supported");
    }

    public void update(float delta) {
        if(currentMap != null) {
            currentMap.update(delta);
        }
    }

    public void setMap(MapAsset mapAsset) {
        if(currentMap != null) {
            currentMap.leave();
        }
        currentMap = mapCache.get(mapAsset);
        currentMapAsset = mapAsset;
        currentMap.load();
        player.connection.sendTCP(new ChangeMapNotifier(currentMap.getEngineId()));
    }

    public MapAsset getFarmMap() {
        return farmMap;
    }

    public IMap getCurrentMap() {
        return currentMap;
    }

    public MapAsset getCurrentMapAsset() {
        return currentMapAsset;
    }
}
