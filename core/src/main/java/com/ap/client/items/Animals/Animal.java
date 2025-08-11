package com.ap.client.items.Animals;

import com.ap.client.component.items.FarmAnimal;
import com.ap.client.model.BarnsType;
import com.ap.client.model.FarmAnimalTypes;
import com.ap.client.screen.maps.MapAdaptor;
import com.ap.client.state.FarmAnimalAnimationState;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class Animal {
    public static final int maxFriendship = 1000;
    public static final int maxHealth = 5;

    private Entity entity;

    private String name;
    private FarmAnimalTypes type;
    private int friendship = 0;
    private float age;
    private AnimalHouse house;

    private boolean isPetToday;
    private boolean isFeedToday;

    //if stay night or hit by axe out one will decrease
    private int health;

    private boolean isInHouse;

    private Vector2 currenPosition;
    private Vector2 inHousePosition;

    public Animal(FarmAnimalTypes type, AnimalHouse house, Vector2 inHousePosition) {
        this.type = type;
        this.house = house;
        name = "FarmAnimal";
        this.inHousePosition = new Vector2(inHousePosition.x, inHousePosition.y);
        currenPosition = new Vector2(inHousePosition.x, inHousePosition.y);
        health = maxHealth;
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
        return house.getTransform().getPosition();
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
