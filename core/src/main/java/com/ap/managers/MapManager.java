package com.ap.managers;

import com.ap.GdxGame;
import com.ap.asset.MapAsset;
import com.ap.screen.GameScreen;
import com.ap.screen.maps.Farm;
import com.ap.screen.maps.House;
import com.ap.screen.maps.IMap;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class MapManager {
    private GdxGame game;
    private GameScreen gameScreen;
    private TransitionManager transitionManager;

    HashMap<MapAsset, IMap> mapCache = new HashMap<>();
    IMap currentMap = null;

    public MapManager(GdxGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.transitionManager = new TransitionManager(game, this);
    }
    public void loadAllMaps() {
        for(MapAsset mapAsset : MapAsset.values()) {
            IMap map = createMap(mapAsset);
            mapCache.put(mapAsset, map);
            map.setup(mapAsset);
        }
    }
    public void changeMap(MapAsset mapAsset) {
        if(transitionManager.isTransitioning()) {
            return;
        }
        transitionManager.initTransition(mapAsset);
    }

    private IMap createMap(MapAsset map) {
        switch (map) {
            case Farm1, Farm2, Forest -> {
                return new Farm(game, gameScreen);
            } case House -> {
                return new House(game, gameScreen);
            }
        }
        throw new IllegalArgumentException("Map " + map.name() + " is not supported");
    }

    public void update(float delta) {
        if(currentMap != null) {
            currentMap.update(delta);
        }
        transitionManager.render(Gdx.graphics.getDeltaTime());
    }

    public void setMap(MapAsset mapAsset) {
        if(currentMap != null) {
            currentMap.leave();
        }
        currentMap = mapCache.get(mapAsset);
        currentMap.load();
    }
}
