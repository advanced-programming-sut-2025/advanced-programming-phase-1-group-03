package com.ap.client.items.Animals;

import com.ap.client.component.Transform;
import com.ap.client.component.items.Barn;
import com.ap.client.model.BarnsType;
import com.ap.client.screen.maps.MapAdaptor;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class AnimalHouse {

    private Transform transform;
    private Barn barnComponent;
    private BarnsType type;
    private int capacity;
    private ArrayList<Animal> animals;

    public AnimalHouse(Entity entity) {
        barnComponent = Barn.mapper.get(entity);
        type = barnComponent.getType();
        capacity = type.getCapacity();
        animals = new ArrayList<>();
        transform = Transform.mapper.get(entity);
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
