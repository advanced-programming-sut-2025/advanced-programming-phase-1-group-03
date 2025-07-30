package com.ap.system;

import com.ap.component.*;
import com.ap.items.EntityFactory;
import com.ap.items.plant.Crop;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class CrowAttackManagerSystem extends IteratingSystem {

    Engine engine;
    World world;

    public CrowAttackManagerSystem(Engine engine, World world) {
        super(Family.all(Crow.class).get());
        this.engine = engine;
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Crow crow = Crow.mapper.get(entity);
        crow.setStateTime(crow.getStateTime() + deltaTime);
        Transform transform = Transform.mapper.get(entity);
        Vector2 position = transform.getPosition();
        Entity player = engine.getEntitiesFor(Family.all(Player.class).get()).first();
        Vector2 playerPosition = Transform.mapper.get(player).getPosition();

        switch (crow.getSituation()) {
            case Walk -> {
                if (Helper.calculateDistance(position, playerPosition) <= Crow.playerNoticingRadius) {
                    crow.setSituation(Crow.Situation.Voice);
                } else if (crow.getStateTime() >= crow.getSituation().stateTimeLimit) {
                    crow.setSituation(Crow.Situation.Voice);
                }
            }
            case Voice -> {
                if (Helper.calculateDistance(position, playerPosition) <= Crow.playerNoticingRadius) {
                    crow.setSituation(Crow.Situation.Fly);
                } else if (crow.getStateTime() >= crow.getSituation().stateTimeLimit) {
                    crow.setSituation(Crow.Situation.Walk);
                }
            }
            case Fly -> {
                if (!crow.isHasEaten()) {
                    Helper.removeEntity(crow.getPurposeEntity(), engine, world);
                    crow.setHasEaten(true);
                }
                if (crow.getStateTime() >= crow.getSituation().stateTimeLimit) {
                    Helper.removeEntity(entity, engine, world);
                }
            }
        }
        float xDelta = crow.getSpeed() * MathUtils.cosDeg(crow.getDirection());
        float yDelta = crow.getSpeed() * MathUtils.sinDeg(crow.getDirection());
        position.set(position.x + xDelta * deltaTime, position.y + yDelta * deltaTime);

    }

    public void onDayChanged() {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(Growable.class).get());
        Array<Entity> attackables = new Array<>();
        for (Entity entity : entities) {
            ItemHolder itemHolder;
            if ((itemHolder = entity.getComponent(ItemHolder.class))== null) continue;
            if (itemHolder.getItem() == null || !(itemHolder.getItem() instanceof Crop)) continue;
            Crop crop = (Crop) itemHolder.getItem();
            if (crop.isGiant()) continue;
            if (Growable.mapper.get(entity).getCurrentStage() == 0) continue;
            attackables.add(entity);
        }

        int newCrowNumber = Math.min(attackables.size / 16, 4);
        for (int i = 0; i < newCrowNumber; i++) {
            if (Helper.random(1, 2) != 1) continue;
            int index = Helper.random(0, attackables.size - 1);
            Entity purpose = attackables.get(index);
            attackables.removeIndex(index);
            engine.addEntity(EntityFactory.instance.createCrowEntity(purpose));
        }

    }

}
