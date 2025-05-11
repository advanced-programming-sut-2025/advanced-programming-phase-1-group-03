package phi.ap.model.items.products;

import phi.ap.model.enums.FruitTypes;
import phi.ap.model.items.producers.Producer;

public class Fruit extends Product {
    private Producer source;
    private FruitTypes fruitType;

    public Fruit(int height, int width, FruitTypes fruitType) {
        super(height, width);
        this.fruitType = fruitType;
        setName(fruitType.getName());
    }

    @Override
    public void doTask() {

    }
}
