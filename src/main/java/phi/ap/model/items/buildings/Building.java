package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Ground;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.Water;

import java.util.ArrayList;

public abstract class Building extends Item {
    public Building(int height, int width) {
        super(height, width);
        setName("Building");
    }

}
