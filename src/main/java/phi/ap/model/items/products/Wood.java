package phi.ap.model.items.products;

import phi.ap.model.enums.StoreProducts.CarpenterShopProducts;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

public class Wood extends Product {
    private int amount = 2;
    public Wood(int height, int width) {
        super(height, width);
        fillTile(TileType.Wood.getTile());
        setName("Wood");
        setSellPrice(CarpenterShopProducts.Wood.getPrice());
    }

    public void doTask() {

    }

    public int getAmount() {
        return amount;
    }
}
