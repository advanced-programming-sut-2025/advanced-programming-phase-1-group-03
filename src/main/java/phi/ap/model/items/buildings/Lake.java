package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Water;

public class Lake extends Building {
    public Lake(int height, int width, Coordinate coordinate) {
        super(height, width);
        setName("Lake");
        setCoordinate(coordinate);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, new Tile(TileType.Lake));
            }
        }
        for(int i = 0; i < getHeight(); i++)
            for(int j = 0; j < getWidth(); j++){
                Water water = new Water();
                water.setCoordinate(new Coordinate(i, j));
                water.fillTile(TileType.Water.getTile());
                water.makeHasFish();
                addItem(water);
            }
    }

    @Override
    public void doTask() {

    }
}
