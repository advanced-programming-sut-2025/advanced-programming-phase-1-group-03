package com.ap.component;

import com.ap.maps.MapAdaptor;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class TwinEntity implements Component {
    //For now just work for without physic
    public static final ComponentMapper<TwinEntity> mapper = ComponentMapper.getFor(TwinEntity.class);
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<MapAdaptor> maps = new ArrayList<>();

    public TwinEntity() {
}

    public void add(Entity entity, MapAdaptor map) {
        entities.add(entity);
        maps.add(map);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<MapAdaptor> getMaps() {
        return maps;
    }

    public void removeAll() {
        for (int i = 0; i < maps.size(); i++) {
            MapAdaptor map = maps.get(i);
            Engine engine = map.getEngine();
            World world = map.getWorld();
            Entity entity = entities.get(i);
            Helper.removeEntity(entity, engine, world);
        }
    }
}
