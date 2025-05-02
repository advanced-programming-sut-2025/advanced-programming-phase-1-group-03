package phi.ap.model.items.products;

import phi.ap.model.enums.FruitTypes;
import phi.ap.model.items.producers.Producer;

public class Fruit extends Product {
    private Producer source;
    private FruitTypes feature;

    public Fruit(int height, int width) {
        super(height, width);
    }

    @Override
    public void doTask() {

    }
}
