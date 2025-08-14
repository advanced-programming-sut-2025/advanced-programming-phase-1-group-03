package com.ap.managers;

import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.component.Animation2D;
import com.ap.component.Transform;
import com.ap.component.items.FarmAnimal;
import com.ap.items.EntityFactory;
import com.ap.items.Item;
import com.ap.items.ItemStack;
import com.ap.items.animal.Animal;
import com.ap.items.animal.AnimalHouse;
import com.ap.maps.MapAdaptor;
import com.ap.model.*;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class AnimalManager {

    private ServerPlayer owner;
    private GameManager gameManager;
    private MapAdaptor farm;
    private ArrayList<AnimalHouse> houses = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();


//    private AnimalStatMenu animalStatMenu;

//
    public AnimalManager(ServerPlayer owner, GameManager gameManager) {
        this.owner = owner;
        this.gameManager = gameManager;
        var mapManager = gameManager.getMapManager();
        farm = (MapAdaptor) mapManager.mapCache.get(new MapManager.MapKey(MapAsset.Farm1, owner));
        if (farm == null) {
            farm = (MapAdaptor) mapManager.mapCache.get(new MapManager.MapKey(MapAsset.Farm2, owner));
        }
    }

    public void placeHouse(BarnsType barnsType, Vector2 position, World world, Engine engine) {
        Entity entity = EntityFactory.instance.CreateBarnEntity(barnsType, position, world, engine);
        AnimalHouse house = new AnimalHouse(entity,
                (MapAdaptor) gameManager.getMapManager().mapCache.get(new MapManager.MapKey(MapAsset.valueOf(barnsType.name()), owner)),
                this);
        houses.add(house);
        Helper.addEntity(entity, engine);
    }

    public AnimalHouse getHouseByMapAsset(MapAsset mapAsset) {
        return houses.stream().filter(
                (AnimalHouse a) -> a.getBarnComponent().getType().name().equals(mapAsset.name())).findFirst().orElse(null);
    }

    public void placeAnimal(FarmAnimalTypes type, Vector2 position) {

        AnimalHouse house = getHouseByMapAsset(gameManager.getMapManager().currentMapAssets.get(owner));
        if (house == null) return;
        Animal animal = new Animal(type, house, position, this);
        var entity = EntityFactory.instance.CreateFarmAnimalEntity(position, house.getMap().getWorld(), animal);
        animal.setEntity(entity);
        Helper.addEntity(entity, house.getMap().getEngine());
        animals.add(animal);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public MapAdaptor getFarm() {
        return farm;
    }

    public ArrayList<AnimalHouse> getHouses() {
        return houses;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ServerPlayer getOwner() {
        return owner;
    }

    public boolean shearSheep(Entity entity, Engine engine, World world) {
        FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
        farmAnimal.setType(FarmAnimalTypes.ShearedSheep);
        farmAnimal.getAnimal().setType(FarmAnimalTypes.ShearedSheep);
        Animation2D.mapper.get(entity).setAtlasKey(FarmAnimalTypes.ShearedSheep.getAtlasKey());
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
        Helper.addEntity(EntityFactory.instance.CreateEmoteEntity(Transform.mapper.get(entity), EmoteType.Noise, 2), engine);
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
        for (Animal animal : animals) {

            if (!animal.isFeedToday()) animal.setFriendship(animal.getFriendship() - 20);
            if (!animal.isInHouse()) animal.setFriendship(animal.getFriendship() - 20);
            if (!animal.isPetToday()) animal.setFriendship(animal.getFriendship() - 10);
            animal.setFriendship(Math.max(animal.getFriendship(), 0));
            animal.setAge(animal.getAge() + 1);
            Helper.removeEntity(animal.getEntity(), animal.isInHouse() ? animal.getHouse().getMap().getEngine() : farm.getEngine(),
                    animal.isInHouse() ? animal.getHouse().getMap().getWorld() : farm.getWorld());
            var entity = EntityFactory.instance.CreateFarmAnimalEntity(animal.getInHousePosition(), animal.getHouse().getMap().getWorld(), animal);
            animal.setEntity(entity);
            Helper.addEntity(entity, animal.getHouse().getMap().getEngine());
            if (!animal.getType().getProducts().isEmpty()) {
                AnimalProducts product = animal.getType().getProducts().get(Helper.random(0, animal.getType().getProducts().size() - 1));
                var p = EntityFactory.instance.CreateCollectableItemEntity(animal.getInHousePosition(), new ItemStack(
                        new Item(product.getUiName(), 10, AtlasAsset.AnimalProducts, product.getAtlasKey(),
                                product.getPrice()), 1));
                Helper.addEntity(p, animal.getHouse().getMap().getEngine());
            }

            animal.setFeedToday(false);
            animal.setPetToday(false);
            animal.setInHouse(true);
        }
    }


}
