package com.ap.client.managers;

import com.ap.client.Constraints;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.component.*;
import com.ap.client.component.items.FarmAnimal;
import com.ap.client.items.Animals.Animal;
import com.ap.client.items.Animals.AnimalHouse;
import com.ap.client.items.EntityFactory;
import com.ap.client.model.BarnsType;
import com.ap.client.model.EmoteType;
import com.ap.client.model.FarmAnimalTypes;
import com.ap.client.screen.GameScreen;
import com.ap.client.screen.maps.MapAdaptor;
import com.ap.client.tiled.TiledPhysic;
import com.ap.client.ui.widget.AnimalStatMenu;
import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class AnimalManager {
    public static AnimalManager instance;
    private GameScreen gameScreen;
    private AnimalStatMenu animalStatMenu;

    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<AnimalHouse> houses = new ArrayList<>();

    public AnimalManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        animalStatMenu = gameScreen.getAnimalStatMenu();
    }

    public boolean createFarmAnimal(FarmAnimalTypes type, Vector2 position, World world, Engine engine, MapAdaptor map) {
        BarnsType houseType = findBarnType(map);
        if (houseType == null) return false;
        AnimalHouse house = findHouse(houseType);
        if (house == null) return false;

        Animal animal = new Animal(type, house, position);
        if (house.isFull()) return false;
        house.addAnimal(animal);
        Entity entity = EntityFactory.instance.CreateFarmAnimalEntity(position, world, animal);
        Helper.addEntity(entity, engine);

        return true;

    }

    public AnimalHouse findHouse(BarnsType type) {
        for (AnimalHouse house : houses) {
            if (house.getType().equals(type)) return house;
        }
        return null;
    }

    public BarnsType findBarnType(MapAdaptor map) {
        switch (map.getMapAsset()) {
            case Barn, BigBarn, DeluxeBarn, Coop, BigCoop, DeluxeCoop -> {
                return BarnsType.valueOf(map.getMapAsset().name());
            }
            default -> {
                return null;
            }
        }
    }

    public void addHouse(AnimalHouse house) {
        houses.add(house);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<AnimalHouse> getHouses() {
        return houses;
    }

    public AnimalStatMenu getAnimalStatMenu() {
        return animalStatMenu;
    }

    public boolean shearSheep(Entity entity, Engine engine, World world) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        farmAnimal.setType(FarmAnimalTypes.ShearedSheep);
        farmAnimal.getAnimal().setType(FarmAnimalTypes.ShearedSheep);
        Animation2D.mapper.get(entity).setAtlastKey(FarmAnimalTypes.ShearedSheep.getAtlasKey());
        Animation2D.mapper.get(entity).setShouldUpdate(true);
        Animal animal = farmAnimal.getAnimal();
        animal.setFriendship(animal.getFriendship() + 5);
        return true;
    }

    public boolean pet(Animal animal) {
        if (!animal.isPetToday()) animal.setFriendship(animal.getFriendship() + 15);
        animal.setPetToday(true);
        return true;
    }

    public boolean feed(Animal animal) {
        if (!animal.isFeedToday()) animal.setFriendship(animal.getFriendship() + 8);
        animal.setFeedToday(true);
        return true;
    }

    public boolean hit(Entity entity, Engine engine, World world) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        Animal animal = farmAnimal.getAnimal();
        animal.setHealth(animal.getHealth() - 1);
        if (animal.getHealth() <= 0) {
            Helper.removeEntity(entity, engine, world);
            removeAnimal(animal);
        }
        engine.addEntity(EntityFactory.instance.CreateEmoteEntity(Transform.mapper.get(entity), EmoteType.Noise, 2));
        return true;
    }

    public boolean milkAnimal(Animal animal) {
        return true;
    }

    public void removeAnimal(Animal animal) {
        animal.getHouse().removeAnimal(animal);
        animals.remove(animal);
    }

    public void onDayChanged() {

    }
}
