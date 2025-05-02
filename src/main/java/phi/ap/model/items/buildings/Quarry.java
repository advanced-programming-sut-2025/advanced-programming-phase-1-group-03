package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;

public class Quarry extends Building {
    public Quarry(int height, int width, Coordinate coordinate) {
        super(height, width);
        setCoordinate(coordinate);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, new Tile(TileType.Quarry));
            }
        }
    }

    @Override
    public void doTask() {

    }
}
