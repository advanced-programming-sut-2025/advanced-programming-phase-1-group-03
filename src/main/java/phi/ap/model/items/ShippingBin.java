package phi.ap.model.items;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.TileType;

public class ShippingBin extends Item{

    public ShippingBin(int height, int width) {
        super(height, width);
        setName("ShippingBin");
        fillTile(TileType.ShippingBin.getTile());
    }
    @Override
    public void doTask() {

    }
}
