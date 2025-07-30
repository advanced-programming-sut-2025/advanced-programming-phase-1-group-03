package phi.ap.model.items.products;

import phi.ap.model.enums.AnimalProductTypes;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.items.Animal;

public class AnimalProduct extends Product {
    AnimalProductTypes animalProductType;
    Tool.BasicToolLevels Tool.BasicToolLevels;
    double productNum;
    Animal animal;
    public AnimalProduct(int height, int width, AnimalProductTypes animalProductType, Tool.BasicToolLevels Tool.BasicToolLevels,
                         double productNum, Animal animal) {
        super(height, width);
        this.animalProductType = animalProductType;
        this.Tool.BasicToolLevels = Tool.BasicToolLevels;
        this.productNum = productNum;
        this.animal = animal;
        setName(animalProductType.getName());
        setSellPrice((int)(animalProductType.getPrice() * productNum));
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
