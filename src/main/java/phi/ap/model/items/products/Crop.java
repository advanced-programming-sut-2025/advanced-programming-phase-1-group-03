package phi.ap.model.items.products;

import phi.ap.model.enums.CropsTypes;
import phi.ap.model.enums.ForagingCropsTypes;
import phi.ap.model.items.producers.Producer;

public class Crop extends Product {
    private Producer source;
    private CropsTypes cropsType = null;
    private ForagingCropsTypes foragingType;

    public Crop(int height, int width, ForagingCropsTypes type) {
        super(height, width);
        this.foragingType = type;
        fillTile(type.getTile());
        setName(type.getName());
    }
    public Crop(int height, int width, CropsTypes cropsType) {
        super(height, width);
        this.cropsType = cropsType;
        setName(cropsType.getName());
    }

    public ForagingCropsTypes getForagingType() {
        return foragingType;
    }

    @Override
    public void doTask() {

    }
}
