package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.items.Item;

import java.util.ArrayList;

public abstract class Building extends Item {
    public Building(int height, int width) {
        super(height, width);
    }
    public void setDoors(ArrayList<Tile> doors) {
        for (Tile door : doors) {
            if (door.getCoordinate() == null) continue;
            setTile(door.getCoordinate().getY(), door.getCoordinate().getX(), door.getTile());
        }
    }
}
