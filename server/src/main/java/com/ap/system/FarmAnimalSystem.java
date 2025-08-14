package com.ap.system;


import com.ap.component.Carrier;
import com.ap.component.Clickable;
import com.ap.component.Move;
import com.ap.component.Transform;
import com.ap.component.items.FarmAnimal;
import com.ap.items.EntityFactory;
import com.ap.items.Item;
import com.ap.items.animal.Animal;
import com.ap.managers.AnimalManager;
import com.ap.model.EmoteType;
import com.ap.model.FarmAnimalTypes;
import com.ap.notifiers.ShowAnimalStatNotifier;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
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
        if (Carrier.mapper.has(entity)) return;
        Move move = Move.mapper.get(entity);
        FarmAnimal animal = FarmAnimal.mapper.get(entity);

        Clickable clickable = Clickable.mapper.get(entity);

        animal.setAnimationStateTime(animal.getAnimationStateTime() + deltaTime);

        if (clickable != null && clickable.isClicked()) {
            onClick(entity);
            clickable.setClicked(false);
        }

        if (animal.getDuration() != -1 && animal.getAnimationStateTime() > animal.getDuration()) {
            if (animal.getDirection().isZero()) animal.setSituation(FarmAnimal.Situation.Idle);
            else animal.setSituation(FarmAnimal.Situation.Walk);
        }

        if (animal.getSituation() == FarmAnimal.Situation.Walk) move.getDirection().set(animal.getDirection());
        else move.getDirection().set(Vector2.Zero);

    }

    public void onClick(Entity entity) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        Animal animal = farmAnimal.getAnimal();
        AnimalManager manager = animal.getAnimalManager();
        Clickable click = Clickable.mapper.get(entity);
        if (click.getButtonClicked() == Input.Buttons.RIGHT) {
            manager.getOwner().connection.sendTCP(new ShowAnimalStatNotifier(animal.getName(), animal.getType().ordinal(),
                    animal.getFriendship(), animal.getAge(), animal.isPetToday(), animal.isFeedToday(), animal.getHealth(),
                    animal.isInHouse()));
            return;
        }

        switch (click.getItemName()) {
            case "Hay" :
                feedWithHay(entity, manager, click);
                break;
            case "Shear" :
                useShear(entity, manager, click);
                break;
            case "MilkPail" :
                useMilkPail(entity, manager, click);
                break;
            case "Axe" :
                useAxe(entity , manager, click);
                break;
            default:
                if (manager.pet(animal)) {
                    Helper.addEntity(EntityFactory.instance.CreateEmoteEntity(Transform.mapper.get(entity), EmoteType.Heart, FarmAnimal.Situation.Pet.timeLimit), getEngine());
                    farmAnimal.setSituation(FarmAnimal.Situation.Pet);
                }
        }


    }

    public void feedWithHay(Entity entity, AnimalManager manager, Clickable click) {

        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        Animal animal = farmAnimal.getAnimal();
        if (manager.feed(animal)) {
            animal.getAnimalManager().getOwner().playerManager.getInventory().removeItem(click.getItemName(), 5);
            farmAnimal.setSituation(FarmAnimal.Situation.Eat);
            animal.setFeedToday(true);
        }
    }
    public void useShear(Entity entity, AnimalManager manager, Clickable click) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        Animal animal = farmAnimal.getAnimal();
        if (animal.getType() == FarmAnimalTypes.Sheep) {
            manager.shearSheep(entity, engine, world);
        }
    }
    public void useMilkPail(Entity entity, AnimalManager manager, Clickable click) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        Animal animal = farmAnimal.getAnimal();
        switch (animal.getType()) {
            case Goat, WhiteCow, BrownCow : manager.milkAnimal(animal); break;
        }
    }
    public void useAxe(Entity entity, AnimalManager manager, Clickable click) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        Animal animal = farmAnimal.getAnimal();
        manager.hit(entity, engine, world);
    }

}
