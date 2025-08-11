package com.ap.system.universal;

import com.ap.Configuration;
import com.ap.notifiers.TimeNotifier;
import com.ap.model.Room;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class NotifySystem extends EntitySystem {
    private final Room room;
    private final TimeSystem timeSystem;

    public NotifySystem(Room room, Engine engine) {
        this.room = room;
        this.timeSystem = engine.getSystem(TimeSystem.class);
    }

    private float timeTimer = 0;

    @Override
    public void update(float deltaTime) {
        timeTimer += deltaTime;

        if(timeTimer >= Configuration.NOTIFY_USERS_TIME_STEP) {
            timeTimer -= Configuration.NOTIFY_USERS_TIME_STEP;

            room.broadcast(new TimeNotifier(timeSystem.getTime()));
        }
    }

    public void mapChanged(TiledMap tiledMap) {

    }
}
