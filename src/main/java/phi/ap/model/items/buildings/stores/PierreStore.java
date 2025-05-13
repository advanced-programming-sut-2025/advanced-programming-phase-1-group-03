package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;

public class PierreStore extends Store {
    public PierreStore(int height, int width) {
        super(height, width, StoreTypes.PierreGeneralStore);
        setCoordinate(new Coordinate(25, 2));
        Text text = new Text("PierreStore", new Coordinate(2, 5));
        addItem(text);
    }
}
