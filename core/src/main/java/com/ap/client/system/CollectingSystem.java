package com.ap.client.system;

import com.ap.client.asset.SoundAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.component.Collectable;
import com.ap.client.component.Container;
import com.ap.client.component.Transform;
import com.ap.client.model.GameData;
import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class CollectingSystem extends IteratingSystem {
    Engine engine;
    World world;
    Entity playerEntity;
    AudioService audioService;
    public CollectingSystem(Engine engine, World world, Entity playerEntity, AudioService audioService) {
        super(Family.all(Collectable.class, Transform.class).get());
        this.engine = engine;
        this.world = world;
        this.playerEntity = playerEntity;
        this.audioService = audioService;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.mapper.get(entity);
        Collectable collectable = Collectable.mapper.get(entity);

        if (!collectable.isActive()) {
            if (Helper.calculateDistance(transform.getMiddlePosition(), Transform.mapper.get(playerEntity).getMiddlePosition())
                    < Collectable.collectingRange) {
                collectable.setActive(true);
            }
        }

        if (collectable.isActive()) {
            //speed will rise on direction of distance Vector
            Vector2 distance = Transform.mapper.get(playerEntity).getMiddlePosition().sub(transform.getMiddlePosition());
            float d2 = distance.x * distance.x + distance.y * distance.y;
            distance.set(distance.x / d2, distance.y / d2);
            collectable.getSpeed().add( collectable.getAcceleration() * deltaTime, collectable.getAcceleration() * deltaTime);
            collectable.getSpeed().set(Math.min(collectable.getSpeed().x, Collectable.maxSpeed), Math.min(collectable.getSpeed().y, Collectable.maxSpeed));
            transform.getPosition().add(collectable.getSpeed().x * distance.x, collectable.getSpeed().y * distance.y);
            if (Helper.calculateDistance(transform.getMiddlePosition(), Transform.mapper.get(playerEntity).getMiddlePosition())
                < Collectable.receivedRange) {
                GameData.getInstance().getInventory().addItem(collectable.getItem());
                Helper.removeEntity(entity, engine, world);
                audioService.playSound(SoundAsset.Gift);
            }
        }
    }
}
