package phi.ap.model.items;

import phi.ap.model.enums.TileType;

public class Dirt extends Item {
    private boolean plowed = false;

    public Dirt() {
        super();
    }

    @Override
    public void doTask() {

    }

    public void plow() {
        plowed = true;
        setTile(0, 0, TileType.PlowedDirt.getTile());
    }
}
