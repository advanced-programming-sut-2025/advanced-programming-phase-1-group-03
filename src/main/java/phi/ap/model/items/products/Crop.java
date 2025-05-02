package phi.ap.model.items.products;

import phi.ap.model.enums.ForagingCropsTypes;
import phi.ap.model.items.producers.Producer;

public class Crop extends Product {
    private Producer source;
    private ForagingCropsTypes foragingType = null;

    public Crop(int height, int width, ForagingCropsTypes type) {
        super(height, width);
        this.foragingType = type;
        fillTile(type.getTile());
    }

    public ForagingCropsTypes getForagingType() {
        return foragingType;
    }

    @Override
    public void doTask() {

    }
}
