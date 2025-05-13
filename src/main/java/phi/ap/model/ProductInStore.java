package phi.ap.model;

import phi.ap.model.items.Item;

public class ProductInStore {
    private Item item;
    private String nameInStore;

    public ProductInStore(Item item, String nameInStore) {
        this.item = item;
        this.nameInStore = nameInStore;
    }

    public Item getItem() {
        return item;
    }

    public String getNameInStore() {
        return nameInStore;
    }
}
