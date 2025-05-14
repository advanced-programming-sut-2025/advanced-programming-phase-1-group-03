package phi.ap.model.items.buildings.stores;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.enums.StoreTypes;

public class JojaMart extends Store{
    public JojaMart(int height, int width) {
        super(height, width, StoreTypes.JojaMart);
        setCoordinate(new Coordinate(31, 22));
        Text text = new Text("JojaMart", new Coordinate(3, 3));
        addItem(text);
    }
}
