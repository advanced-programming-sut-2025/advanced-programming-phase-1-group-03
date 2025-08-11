package com.ap.managers;

import com.ap.GdxGame;
import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.component.*;
import com.ap.model.Season;
import com.ap.notifiers.ChangeSeasonNotifier;
import com.ap.screen.GameScreen;
import com.ap.screen.maps.GMap;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;
import java.util.Map;

public class NetworkGameManager {
    private final Map<Integer, GMap> maps = new HashMap<>();
    private final Map<Integer, Entity> entities = new HashMap<>();

    private final GameScreen gameScreen;
    private final GdxGame game;
    private GMap activeMap;

    private final TransitionManager transitionManager;

    public NetworkGameManager(GameScreen gameScreen, GdxGame game) {
        this.gameScreen = gameScreen;
        this.game = game;
        transitionManager = new TransitionManager(game, this::setMap);
    }



    public void updateEntity(int engineId, int itemId, Component[] components) {
        var map = maps.get(engineId);
        assert map != null;

        Entity entity;
        if(entities.containsKey(itemId)) {
            entity = entities.get(itemId);
        } else {
            entity = map.createEntity();
            entity.add(new Network(itemId));
            entities.put(itemId, entity);
        }

        for(Component component : components) {
            if(component instanceof Graphic graphic) {
                if(Graphic.mapper.has(entity)) {
                    Graphic.mapper.get(entity).set(graphic);
                } else {
                    entity.add(graphic);
                }
            } else if(component instanceof Transform transform) {
                if(Transform.mapper.has(entity)) {
                    Transform.mapper.get(entity).set(transform);
                } else {
                    entity.add(transform);
                }
                if(!Movement.mapper.has(entity)) {
                    entity.add(new Movement());
                }

                Transform t = Transform.mapper.get(entity);
                Movement m = Movement.mapper.get(entity);

                m.prevPos.set(t.getPosition());

                m.targetPos.set(transform.getPosition());

                m.accumulator = 0f;

            } else if(component instanceof Player player) {
                if(!Player.mapper.has(entity)) {
                    entity.add(player);
                }
                if(!Controller.mapper.has(entity)) {
                    entity.add(new Controller());
                }
            } else if(component instanceof Facing facing) {
                if(!Facing.mapper.has(entity)) {
                    entity.add(facing);
                } else {
                    Facing.mapper.get(entity).set(facing);
                }
            }
        }
    }

    public void setAnimation(String atlasKey, AtlasAsset atlasAsset, int entityId, float speed, Animation.PlayMode playMode) {
        Entity entity = entities.get(entityId);
        assert entity != null;
        if(Animation2D.mapper.has(entity)) {
            Animation2D animation2D = Animation2D.mapper.get(entity);
            animation2D.set(atlasAsset, atlasKey, playMode);
        } else {
            Animation2D anim = new Animation2D(atlasAsset, atlasKey, speed, playMode);
            entity.add(anim);
        }
    }


    public void createMap(MapAsset mapAsset, int engineId, boolean showWeather, boolean tileSelection) {
        var map = new GMap(gameScreen, game, engineId, mapAsset);
        if(showWeather) {
            map.showWeather();
        }
        if(tileSelection) {
            map.tileSelectionSystem();
        }
        map.setup();
        maps.put(engineId, map);
    }

    public void changeMap(int engineId) {
        if(transitionManager.isTransitioning()) {
            return;
        }
        transitionManager.initTransition(engineId);
    }

    private void setMap(int engineId) {
        if(activeMap != null) {
            activeMap.leave();
        }
        activeMap = maps.get(engineId);
        activeMap.load();
    }

    public void update(float delta) {
        if(activeMap != null) {
            activeMap.update(delta);
        }
        if(transitionManager.isTransitioning()) {
            transitionManager.render(delta);
        }
    }

    public void removeEntity(int entityId, int engineId) {
        var map = maps.get(engineId);
        assert map != null;
        var entity = entities.get(entityId);
        assert entity != null;
        map.removeEntity(entity);
    }

    public void seasonChanged(Season newSeason) {
        for(GMap map : maps.values()) {
            map.changeSeasonTileset(newSeason);
        }
    }
}
