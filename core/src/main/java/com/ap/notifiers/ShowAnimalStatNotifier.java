package com.ap.notifiers;

public class ShowAnimalStatNotifier {
    public String name;
    public int typeOrdinal;
    public int friendship;
    public float age;
    public boolean isPetToday;
    public boolean isFeedToday;
    public int health;
    public boolean isInHouse;

    public ShowAnimalStatNotifier(String name, int typeOrdinal, int friendShip, float age, boolean isPetToday, boolean isFeedToday, int health, boolean isInHouse) {
        this.name = name;
        this.typeOrdinal = typeOrdinal;
        this.friendship = friendShip;
        this.age = age;
        this.isPetToday = isPetToday;
        this.isFeedToday = isFeedToday;
        this.health = health;
        this.isInHouse = isInHouse;
    }

    public ShowAnimalStatNotifier() {
    }

    public String getName() {
        return name;
    }

    public int getTypeOrdinal() {
        return typeOrdinal;
    }

    public int getFriendship() {
        return friendship;
    }

    public float getAge() {
        return age;
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
}
