package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;

public class MarnieRenchStore extends Store{
    public MarnieRenchStore(int height, int width) {
        super(height, width, StoreTypes.MarnieRanch);
        setCoordinate(new Coordinate(3, 20));
        Text text = new Text("MarnieRanch", new Coordinate(3, 2));
        addItem(text);
    }
}
