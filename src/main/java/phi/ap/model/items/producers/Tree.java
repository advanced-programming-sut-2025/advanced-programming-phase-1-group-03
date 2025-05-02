package phi.ap.model.items.producers;

import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;
import phi.ap.model.enums.TreeTypes;

public class Tree extends Producer {
    private final TreeTypes type;

    public Tree(TreeTypes type ,int height, int width) {
        super(height, width);
        this.type = type;
        fillTile(type.getTile());
    }

    public TreeTypes getType() {
        return type;
    }


    @Override
    public void produce() {

    }

    @Override
    public void doTask() {

    }


}
