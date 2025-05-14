package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;

public class FishShop extends Store{
    public FishShop(int height, int width) {
        super(height, width, StoreTypes.FishShop);
        setCoordinate(new Coordinate(9, 2));
        Text text = new Text("FishShop", new Coordinate(1, 3));
        addItem(text);
    }
}
