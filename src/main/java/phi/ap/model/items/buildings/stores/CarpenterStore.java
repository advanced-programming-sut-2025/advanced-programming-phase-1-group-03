package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;

public class CarpenterStore extends Store {
    public CarpenterStore(int height, int width) {
        super(height, width, StoreTypes.CarpenterShop);
        setCoordinate(new Coordinate(26, 24));
        Text text = new Text("Carpenter", new Coordinate(1, 2));
        addItem(text);
    }
}
