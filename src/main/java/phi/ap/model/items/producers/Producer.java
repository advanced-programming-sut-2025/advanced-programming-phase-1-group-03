package phi.ap.model.items.producers;

import phi.ap.model.items.Item;
import phi.ap.model.items.products.Product;

import java.util.ArrayList;

public abstract class Producer extends Item {
    private ArrayList<Product> produced;

    public Producer(int height, int width) {
        super(height, width);
    }

    public abstract void produce();
}
