package com.ap.client.component;

import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.Map;

import java.util.ArrayList;

public class TwinEntity implements Component {
    //For now just work for without physic
    public static final ComponentMapper<TwinEntity> mapper = ComponentMapper.getFor(TwinEntity.class);
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Engine> engines = new ArrayList<>();

    public TwinEntity() {
    }

    public void add(Entity entity, Engine engine) {
        entities.add(entity);
        engines.add(engine);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public void removeAll() {
        for (int i = 0; i < engines.size(); i++) {
            Engine engine = engines.get(i);
            Entity entity = entities.get(i);
            engine.removeEntity(entity);
        }
    }
}
