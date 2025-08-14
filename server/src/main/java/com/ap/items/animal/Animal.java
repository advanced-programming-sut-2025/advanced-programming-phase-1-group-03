package com.ap.items.animal;

import com.ap.Constraints;
import com.ap.managers.AnimalManager;
import com.ap.model.FarmAnimalTypes;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class Animal {

    private Entity entity;

    private String name;
    private FarmAnimalTypes type;
    private int friendship = 0;
    private float age;
    private AnimalHouse house;

    private AnimalManager animalManager;

    private boolean isPetToday;
    private boolean isFeedToday;

    //if stay night or hit by axe out one will decrease
    private int health;

    private boolean isInHouse = true;

    private Vector2 currenPosition;
    private Vector2 inHousePosition;

    public Animal(FarmAnimalTypes type, AnimalHouse house, Vector2 inHousePosition, AnimalManager animalManager) {
        this.type = type;
        this.house = house;
        name = "FarmAnimal";
        this.inHousePosition = new Vector2(inHousePosition.x, inHousePosition.y);
        currenPosition = new Vector2(inHousePosition.x, inHousePosition.y);
        health = Constraints.ANIMAL_MAX_HEALTH;
        this.animalManager = animalManager;
    }

    public AnimalManager getAnimalManager() {
        return animalManager;
    }

    public Vector2 getInHousePosition() {
        return inHousePosition;
    }

    public void setInHousePosition(Vector2 inHousePosition) {
        this.inHousePosition = inHousePosition;
    }

    public String getName() {
        return name;
    }

    public FarmAnimalTypes getType() {
        return type;
    }

    public int getFriendship() {
        return friendship;
    }

    public float getAge() {
        return age;
    }

    public AnimalHouse getHouse() {
        return house;
    }

    public boolean isPetToday() {
        return isPetToday;
    }

    public boolean isFeedToday() {
        return isFeedToday;
    }

    public int getHealth() {
        return health;
    }

    public boolean isInHouse() {
        return isInHouse;
    }

    public Vector2 getHousePosition() {
        return house.getTransform().getMiddlePosition();
    }

    public Vector2 getCurrenPosition() {
        return currenPosition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(FarmAnimalTypes type) {
        this.type = type;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public void setHouse(AnimalHouse house) {
        this.house = house;
    }

    public void setPetToday(boolean petToday) {
        isPetToday = petToday;
    }

    public void setFeedToday(boolean feedToday) {
        isFeedToday = feedToday;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setInHouse(boolean inHouse) {
        isInHouse = inHouse;
    }

    public void setCurrenPosition(Vector2 currenPosition) {
        this.currenPosition = currenPosition;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }


}