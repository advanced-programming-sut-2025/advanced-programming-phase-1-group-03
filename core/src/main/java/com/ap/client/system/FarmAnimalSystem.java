package com.ap.client.system;

import com.ap.client.component.Facing;
import com.ap.client.component.Move;
import com.ap.client.component.Transform;
import com.ap.client.component.items.FarmAnimal;
import com.ap.client.component.items.FarmAnimal.Situation;
import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

public class FarmAnimalSystem extends IteratingSystem {

    private Engine engine;
    private World world;

    public FarmAnimalSystem(Engine engine, World world) {
        super(Family.all(FarmAnimal.class).get());
        this.engine = engine;
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.mapper.get(entity);
        Transform transform = Transform.mapper.get(entity);
        FarmAnimal animal = FarmAnimal.mapper.get(entity);

        animal.setAnimationStateTime(animal.getAnimationStateTime() + deltaTime);

        if (animal.getAnimationStateTime() >= 3f) {
            animal.setSituation(Situation.values()[(animal.getSituation().ordinal() + 1) % 5]);
            if (animal.getSituation() == Situation.Walk) {
                move.getDirection().x = Helper.random(-1, 1);
                move.getDirection().y = Helper.random(-1, 1);
                if (move.getDirection().isZero()) move.getDirection().x = -1;
            } else {
                move.getDirection().setZero();
            }
            System.out.println(animal.getSituation());
        }

    }
}
