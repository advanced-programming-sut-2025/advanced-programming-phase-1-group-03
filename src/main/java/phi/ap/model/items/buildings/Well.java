package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Water;

public class Well extends Building {
    public Well(int height, int width) {
        super(height, width);
        setName("Well");
        for(int i = 0; i < getHeight(); i++)
            for(int j = 0; j < getWidth(); j++){
                Water water = new Water();
                water.setCoordinate(new Coordinate(i, j));
                water.fillTile(TileType.Water.getTile());
                addItem(water);
            }
    }

    @Override
    public void doTask() {

    }
}
