package phi.ap.model.enums.StoreProducts;

import phi.ap.model.items.Item;

public interface StoreItemProducer {
    Item getItem();
    String getNameInStore();
}
