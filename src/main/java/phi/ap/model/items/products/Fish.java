package phi.ap.model.items.products;

import phi.ap.model.enums.FishTypes;

public class Fish extends Product {
    private FishTypes fishTypes;

    public Fish(int height, int width, FishTypes fishTypes) {
        super(height, width);
        this.fishTypes = fishTypes;
    }

    @Override
    public void doTask() {

    }
}
