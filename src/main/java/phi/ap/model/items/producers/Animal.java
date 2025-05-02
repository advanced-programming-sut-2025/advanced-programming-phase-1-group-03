package phi.ap.model.items.producers;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.AnimalTypes;

public class Animal extends Producer {
    private AnimalTypes type;

    public ItemStack feedAnimal(ItemStack stack) {
        return null;
    }

    Animal(AnimalTypes animalType, int height, int width) {
        super(height, width);
        this.type = animalType;
    }
    @Override
    public void produce() {

    }

    @Override
    public void doTask() {

    }
}
