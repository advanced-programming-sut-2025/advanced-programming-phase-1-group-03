package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;

public class Greenhouse extends Building {
    public Greenhouse(int height, int width, Coordinate coordinate) {
        super(height, width);
        setCoordinate(coordinate);
        fillTile(TileType.Greenhouse.getTile());
        setWalls();
    }

    @Override
    public void doTask() {

    }
}
