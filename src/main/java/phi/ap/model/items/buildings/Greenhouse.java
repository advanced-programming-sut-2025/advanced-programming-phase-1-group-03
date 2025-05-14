package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.Portal;
import phi.ap.model.items.Water;

public class Greenhouse extends Building {
    public static final int woodRequired = 500;
    public static final int goldRequired = 1000;
    boolean isBuilt = false;
    public Greenhouse(int height, int width, Coordinate coordinate) {
        super(height, width);
        setName("Greenhouse");
        setCoordinate(coordinate);
        fillTile(TileType.Greenhouse.getTile());
        setWalls();
    }

    public boolean isBuilt() {
        return isBuilt;
    }

    public void build() {
        isBuilt = true;
        Portal.makeMiddleDoor(this, getFather(), TileType.Door.getTile());
        for (int i = 2; i < getHeight() - 2; i++) {
            for (int j = 2; j < getWidth() - 2; j++) {
                Dirt dirt = new Dirt();
                dirt.setCoordinate(new Coordinate(i, j));
                addItem(dirt);
            }
        }
        for (int i = 1; i <= getHeight() - 2; i++) {
            setTile(i, 1, TileType.GreenhouseMargin.getTile());
            setTile(i, getWidth() - 2, TileType.GreenhouseMargin.getTile());
        }
        for (int j = 1; j <= getWidth() - 2; j++) {
            setTile(1, j, TileType.GreenhouseMargin.getTile());
            setTile(getHeight() - 2, j, TileType.GreenhouseMargin.getTile());
        }
        Water water = new Water();
        water.setCoordinate(new Coordinate(1, getWidth() / 2));
        addItem(water);
    }

    public void removeItem(Item item) {
        super.removeItem(item);
        for (int i = 0; i < item.getHeight(); i++) {
            for (int j = 0; j < item.getWidth(); j++) {
                int y = i + item.getCoordinate().getY();
                int x = j + item.getCoordinate().getX();
                if (y >= 2 && y < getHeight() - 2 && x >= 2 && x < getWidth() - 2) {
                    Dirt dirt = new Dirt();
                    dirt.setCoordinate(new Coordinate(y, x));
                    addItem(dirt);
                }
            }
        }
    }

    @Override
    public void doTask() {

    }
}
