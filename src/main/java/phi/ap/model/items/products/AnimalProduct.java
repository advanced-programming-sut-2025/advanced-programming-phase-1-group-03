package phi.ap.model.items.products;

import phi.ap.model.enums.AnimalProductTypes;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.Animal;

public class AnimalProduct extends Product {

    AnimalProductTypes animalProductType;
    LevelName levelName;
    double productNum;
    Animal animal;
    public AnimalProduct(int height, int width, AnimalProductTypes animalProductType, LevelName levelName,
                         double productNum, Animal animal) {
        super(height, width);
        this.animalProductType = animalProductType;
        this.levelName = levelName;
        this.productNum = productNum;
        this.animal = animal;
        setName(animalProductType.getName());
        setSellPrice(animalProductType.getPrice());
        setSellable(true);
    }
    public AnimalProduct(int height, int width, AnimalProductTypes animalProductType) {
        super(height, width);
        this.animalProductType = animalProductType;
        setName(animalProductType.getName());
        setSellPrice(animalProductType.getPrice());
        setSellable(true);
    }

    public AnimalProductTypes getAnimalProductType() {
        return animalProductType;
    }

    @Override
    public void doTask() {

    }
}
