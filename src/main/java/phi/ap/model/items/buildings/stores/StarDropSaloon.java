package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;

public class StarDropSaloon extends Store {
    public StarDropSaloon(int height, int width) {
        super(height, width, StoreTypes.TheStarDropSaloon);
        setCoordinate(new Coordinate(33, 2));
        Text text = new Text("StarDrop", new Coordinate(2, 3));
        addItem(text);
    }
}
