package com.ap.system.universal;

import com.ap.component.*;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.ItemNotifier;
import com.ap.notifiers.RemoveEntityNotifier;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class NetworkEntitySystem extends IteratingSystem implements EntityListener {
    private final Array<ServerPlayer> players;
    private int engineId;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(Family.all(Network.class).get(), this);
        engineId = Helper.getEngineId(engine);
    }

    public NetworkEntitySystem(Array<ServerPlayer> players) {
        super(Family.all(Network.class).get());
        this.players = players;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Network network = Network.mapper.get(entity);

        if(Player.mapper.has(entity) && !Player.mapper.get(entity).isNotified) {
            for(ServerPlayer player : players) {
                System.out.println(Player.mapper.get(entity).id);
                if(player.id == Player.mapper.get(entity).id) {
                    player.connection.sendTCP(new ItemNotifier(engineId, network.getId(), Player.mapper.get(entity)));
                }
            }
            Player.mapper.get(entity).isNotified = true;

        }

        if(Transform.mapper.has(entity) && Transform.mapper.get(entity).isChanged()) {
            Transform transform = Transform.mapper.get(entity);
            Helper.sendToAll(players, new ItemNotifier(engineId, network.getId(), transform));
            transform.setChanged(false);
        }
        if(Graphic.mapper.has(entity) && Graphic.mapper.get(entity).isChanged()) {
            Graphic graphic = Graphic.mapper.get(entity);
            Helper.sendToAll(players, new ItemNotifier(engineId, network.getId(), graphic));
            graphic.setChanged(false);
        }
        if(Facing.mapper.has(entity) && Facing.mapper.get(entity).isChanged()) {
            Facing facing = Facing.mapper.get(entity);
            Helper.sendToAll(players, new ItemNotifier(engineId, network.getId(), facing));
            facing.setChanged(false);
        }
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        Helper.sendToAllTCP(players, new RemoveEntityNotifier(engineId, Network.mapper.get(entity).getId()));
    }

    public void shouldSend() {
        for(Entity entity : getEntities()) {
            if(Facing.mapper.has(entity)) {
                Facing.mapper.get(entity).setChanged(true);
            }
            if(Graphic.mapper.has(entity)) {
                Graphic.mapper.get(entity).setChanged(true);
            }
            if(Transform.mapper.has(entity)) {
                Transform.mapper.get(entity).setChanged(true);
            }
            if(Player.mapper.has(entity)) {
                Player.mapper.get(entity).isNotified = false;
            }
        }
    }

}
