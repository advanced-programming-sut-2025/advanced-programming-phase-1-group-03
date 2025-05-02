package phi.ap.model.items.products;

import phi.ap.model.enums.ForagingMineralTypes;
import phi.ap.model.items.Item;

public class Mineral extends Product {
    private ForagingMineralTypes foragingType = null;

    public Mineral(int height, int width, ForagingMineralTypes foragingType) {
        super(height, width);
        this.foragingType = foragingType;
        fillTile(foragingType.getTile());
    }

    public ForagingMineralTypes getForagingType() {
        return foragingType;
    }

    @Override
    public void doTask() {

    }


}
