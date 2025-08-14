package com.ap.items.animal;

import com.ap.component.Transform;
import com.ap.component.items.Barn;
import com.ap.managers.AnimalManager;
import com.ap.managers.GameManager;
import com.ap.maps.MapAdaptor;
import com.ap.model.BarnsType;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

public class AnimalHouse {

    private Transform transform;
    private Barn barnComponent;
    private BarnsType type;
    private int capacity;
    private ArrayList<Animal> animals;
    private MapAdaptor map;
    private AnimalManager animalManager;

    public AnimalHouse(Entity entity, MapAdaptor map, AnimalManager animalManager) {
        barnComponent = Barn.mapper.get(entity);
        type = barnComponent.getType();
        capacity = type.getCapacity();
        animals = new ArrayList<>();
        transform = Transform.mapper.get(entity);
        this.map = map;
        this.animalManager = animalManager;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public MapAdaptor getMap() {
        return map;
    }

    public AnimalManager getAnimalManager() {
        return animalManager;
    }

    public Transform getTransform() {
        return transform;
    }

    public Barn getBarnComponent() {
        return barnComponent;
    }

    public BarnsType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public boolean isFull() {
        return animals.size() >= capacity;
    }
}