package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;

public class Quarry extends Building {
    public Quarry(int height, int width, Coordinate coordinate) {
        super(height, width);
        setName("Quarry");
        setCoordinate(coordinate);
        fillTile(TileType.Quarry.getTile());
        setTile(0, 0, TileType.Pillar.getTile());
        setTile(0, getWidth() - 1, TileType.Pillar.getTile());
        setTile(getHeight() - 1, getWidth() - 1, TileType.Pillar.getTile());
        setTile(getHeight() - 1, 0, TileType.Pillar.getTile());
    }

    @Override
    public void doTask() {

    }
}
