package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;

import java.util.ArrayList;

public class Cottage extends Building {
    public Cottage(int height, int width, Coordinate coordinate) {
        super(height, width);
        setName("Cottage");
        setCoordinate(coordinate);
        fillTile(TileType.Cottage.getTile());
        setWalls();
    }

    @Override
    public void doTask() {

    }

}
