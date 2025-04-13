package phi.ap.model.items.producers;

import phi.ap.model.items.Item;
import phi.ap.model.items.products.Product;

import java.util.ArrayList;

public abstract class Producer extends Item {
    private ArrayList<Product> produced;
    public abstract void produce();
}
