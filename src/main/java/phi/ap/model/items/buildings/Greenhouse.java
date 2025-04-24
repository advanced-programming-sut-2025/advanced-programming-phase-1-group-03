package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.TileType;

public class Greenhouse extends Building {
    public Greenhouse(int height, int width, Coordinate coordinate) {
        super(height, width);
        setCoordinate(coordinate);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, TileType.Greenhouse);
            }
        }
    }

    @Override
    public void doTask() {

    }
}
