package phi.ap.model.items.products;

import phi.ap.model.Tile;
import phi.ap.model.enums.StoneTypes;
import phi.ap.model.enums.TileType;

public class Stone extends Product {
    private StoneTypes type;

    public Stone(int height, int width, StoneTypes type) {
        super(height, width);
        this.type = type;
        setName(type.name());
        fillTile(type.getTile());
    }

    @Override
    public void doTask() {

    }
}
