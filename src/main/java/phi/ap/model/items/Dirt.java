package phi.ap.model.items;

import phi.ap.model.enums.TileType;

public class Dirt extends Item {
    private boolean plowed = false;

    public Dirt() {
        super();
        setName("Dirt");
    }

    @Override
    public void doTask() {

    }

    public void plow() {
        plowed = true;
        setTile(0, 0, TileType.PlowedDirt.getTile());
    }
    public void unPlow(){
        plowed = false;
        setTile(0, 0, TileType.Dirt.getTile());
    }

    public boolean isPlowed() {
        return this.plowed;
    }
}
