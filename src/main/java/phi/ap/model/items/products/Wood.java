package phi.ap.model.items.products;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

public class Wood extends Product {
    public Wood(int height, int width) {
        super(height, width);
        fillTile(TileType.Wood.getTile());
    }

    public void doTask() {

    }
}
