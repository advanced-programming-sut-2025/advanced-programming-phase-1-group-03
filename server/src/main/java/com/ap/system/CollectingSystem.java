package com.ap.system;


import com.ap.asset.SoundAsset;
import com.ap.component.Collectable;
import com.ap.component.Transform;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.PlaySoundNotifier;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class CollectingSystem extends IteratingSystem {
    Engine engine;
    World world;

    private Array<ServerPlayer> players;

    public CollectingSystem(Engine engine, World world,Array<ServerPlayer> players) {
        super(Family.all(Collectable.class, Transform.class).get());
        this.engine = engine;
        this.world = world;
        this.players = players;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.mapper.get(entity);
        Collectable collectable = Collectable.mapper.get(entity);

        for (ServerPlayer player : players) {
            Entity playerEntity = player.playerManager.getPlayerEntity();
            if (playerEntity == null) continue;
            if (!collectable.isActive()) {
                if (Helper.calculateDistance(transform.getMiddlePosition(), Transform.mapper.get(playerEntity).getMiddlePosition())
                        < Collectable.collectingRange) {
                    collectable.setActive(true);
                    collectable.setPlayerId(player.id);
                    break;
                }
            }
        }

        if (collectable.isActive()) {
            //speed will rise on direction of distance Vector
            ServerPlayer player = getPlayer(collectable.getPlayerId());
            if (player == null) {
                collectable.setActive(false);
                return;
            }
            Entity playerEntity = player.playerManager.getPlayerEntity();
            if (playerEntity == null) {
                collectable.setActive(false);
                return;
            }

            Vector2 distance = Transform.mapper.get(playerEntity).getMiddlePosition().sub(transform.getMiddlePosition());
            float d2 = distance.x * distance.x + distance.y * distance.y;
            distance.set(distance.x / d2, distance.y / d2);
            collectable.getSpeed().add( collectable.getAcceleration() * deltaTime, collectable.getAcceleration() * deltaTime);
            collectable.getSpeed().set(Math.min(collectable.getSpeed().x, Collectable.maxSpeed), Math.min(collectable.getSpeed().y, Collectable.maxSpeed));
            transform.getPosition().add(collectable.getSpeed().x * distance.x, collectable.getSpeed().y * distance.y);
            if (Helper.calculateDistance(transform.getMiddlePosition(), Transform.mapper.get(playerEntity).getMiddlePosition())
                < Collectable.receivedRange) {
                player.playerManager.getInventory().addItem(collectable.getItem());
                Helper.removeEntity(entity, engine, world);
                player.connection.sendTCP(new PlaySoundNotifier(0.4f, SoundAsset.Gift));
            }
        }
    }

    private ServerPlayer getPlayer(int id) {
        for (ServerPlayer player : players) {
            if (player.id == id) return player;
        }
        return null;
    }

}
