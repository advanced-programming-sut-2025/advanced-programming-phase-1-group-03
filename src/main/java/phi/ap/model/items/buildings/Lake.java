package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;

public class Lake extends Building {
    public Lake(int height, int width, Coordinate coordinate) {
        super(height, width);
        setCoordinate(coordinate);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, new Tile(TileType.Lake));
            }
        }
    }

    @Override
    public void doTask() {

    }
}
