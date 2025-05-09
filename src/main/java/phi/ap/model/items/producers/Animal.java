package phi.ap.model.items.producers;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.AnimalTypes;

public class Animal extends Producer {
    private AnimalTypes type;
    private int friendShipAmount = 0;

    public ItemStack feedAnimal(ItemStack stack) {
        return null;
    }

    public Animal(AnimalTypes animalType, int height, int width) {
        super(height, width);
        this.type = animalType;
    }

    public AnimalTypes getType() {
        return type;
    }

    public void setType(AnimalTypes type) {
        this.type = type;
    }
    @Override
    public void produce() {

    }

    @Override
    public void doTask() {

    }
}
