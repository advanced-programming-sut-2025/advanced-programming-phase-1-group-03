package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;

public class BlackSmithStore extends Store {
    public BlackSmithStore(int height, int width) {
        super(height, width, StoreTypes.Blacksmith);
        setCoordinate(new Coordinate(2, 2));
        Text text = new Text("BlackSmith", new Coordinate(2, 2));
        addItem(text);
    }
}
