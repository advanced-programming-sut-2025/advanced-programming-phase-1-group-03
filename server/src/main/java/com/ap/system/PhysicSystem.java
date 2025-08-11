package com.ap.system;

import com.ap.asset.MapAsset;
import com.ap.component.*;
import com.ap.component.Transform;
import com.ap.items.Item;
import com.ap.managers.MapManager;
import com.ap.managers.PlayerManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicSystem extends IteratingSystem implements EntityListener, ContactListener {
    private World world;
    private float interval;
    private float accumulator = 0;
    private Engine engine;
    private MapManager mapManager;
    private final PlayerManager playerManager;

    public PhysicSystem(World world, float interval,
                        MapManager mapManager,
                        Engine engine,
                        PlayerManager playerManager) {
        super(Family.all(Physic.class, Transform.class).get());
        this.playerManager = playerManager;
        this.world = world;
        this.engine = engine;
        this.mapManager = mapManager;
        this.interval = interval;
        world.setContactListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        Physic physic = Physic.mapper.get(entity);
        physic.getPreviousPosition().set(physic.getBody().getPosition());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(getFamily(), this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void update(float deltaTime) {
        accumulator += deltaTime;

        int maxSteps = 10;
        int stepCount = 0;

        while (accumulator >= interval && stepCount < maxSteps) {
            accumulator -= interval;
            stepCount++;

            super.update(interval);

            float startTime = System.nanoTime();
            world.step(interval, 6, 2);
            float duration = (System.nanoTime() - startTime) / 1_000_000f;
            if (duration > 16) {
                Gdx.app.log("Warning", "World step took too long: " + duration + "ms, Body count: " + world.getBodyCount());
            }
        }

        world.clearForces();

        // alpha is a number between 0 and 1
        float alpha = accumulator / interval;

        // We do this because we want smooth movement
        for(Entity entity : getEntities()) {
            // If it has move component
            if(Move.mapper.has(entity)) {
                updateTransform(entity, alpha);
            }
        }
    }


    private void updateTransform(Entity entity, float alpha) {
        Transform transform = Transform.mapper.get(entity);
        Physic physic = Physic.mapper.get(entity);

        transform.setPosition(physic.getBody().getPosition().x,
                physic.getBody().getPosition().y);
    }

    @Override
    public void entityAdded(Entity entity) {
    }

    @Override
    public void entityRemoved(Entity entity) {
        // !!! Important !!!
        // This does not work if the Physic component gets removed from an entity
        // because the component is no longer accessible here.
        // This ONLY works when an entity with a Physic component gets removed entirely from the engine.
        Physic physic = Physic.mapper.get(entity);
        if (physic != null) {
            Body body = physic.getBody();
            body.getWorld().destroyBody(body);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Object userDataA = fixtureA.getBody().getUserData();
        Fixture fixtureB = contact.getFixtureB();
        Object userDataB = fixtureB.getBody().getUserData();
//
//        menuOpener(userDataA, userDataB);
//        menuOpener(userDataB, userDataA);
//
        var map = isSpawner(userDataA, userDataB);
        if(map != null) {
            changeMap(map);
        }
        map = isSpawner(userDataB, userDataA);
        if(map != null) {
            changeMap(map);
        }

        Item item = isPlayerItemInteract(userDataA, userDataB);
        if(item != null) {
            item.interact(fixtureA.getBody(), engine, playerManager);
        }
        item = isPlayerItemInteract(userDataB, userDataA);
        if (item != null) {
            item.interact(fixtureB.getBody(), engine, playerManager);
        }

    }
    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Object userDataA = fixtureA.getBody().getUserData();
        Fixture fixtureB = contact.getFixtureB();
        Object userDataB = fixtureB.getBody().getUserData();

       // exitMenu(userDataA, userDataB);
       // exitMenu(userDataB, userDataA);
    }

    private void changeMap(MapAsset map) {
        // prevent moving to broken greenhouse
        if(map == MapAsset.Greenhouse && !playerManager.isGreenhouseBuilt()) {
            return;
        }
        mapManager.setMap(map);
    }

    private Item isPlayerItemInteract(Object userDataA, Object userDataB) {
        if((!(userDataA instanceof Entity entityA)) || (!(userDataB instanceof Entity entityB))) {
            return null;
        }
        if(ItemHolder.mapper.has(entityA) && Player.mapper.has(entityB)) {
            return ItemHolder.mapper.get(entityA).getItem();
        }
        return null;
    }

//    private void menuOpener(Object userDataA, Object userDataB) {
//        if(userDataA instanceof Entity entity &&
//                Player.mapper.has(entity) &&
//                userDataB instanceof String str) {
//            Menus menu = null;
//            try {
//                menu = Menus.valueOf(str);
//            }catch(Exception ignored) {}
//            if(menu != null) {
//                GameUIManager.instance.displayMenu(menu, storeManager::onBuy);
//            }
//        }
//    }
//    private void exitMenu(Object userDataA, Object userDataB) {
//        if(userDataA instanceof Entity entity &&
//                Player.mapper.has(entity) &&
//                userDataB instanceof String str) {
//            Menus menu = null;
//            try {
//                menu = Menus.valueOf(str);
//            }catch(Exception ignored) {}
//            if(menu != null) {
//                GameUIManager.instance.exitMenu(menu);
//            }
//        }
//    }
    private MapAsset isSpawner(Object userDataA, Object userDataB) {
        if(userDataA instanceof String str) {
            MapAsset map;

            if(str.equals("Farm")) {
                map = playerManager.getFarmMap();
            } else {
                try {
                    map = MapAsset.valueOf(str);
                } catch (Exception ignore) { map = null; }
            }

            if(map == null) {
                return null;
            }
            if(!(userDataB instanceof Entity entityB)) {
                return null;
            }
            if(!Player.mapper.has(entityB)) {
                return null;
            }
            return map;
        }
        return null;
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
