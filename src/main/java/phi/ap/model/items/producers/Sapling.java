package phi.ap.model.items.producers;

import phi.ap.model.enums.SaplingTypes;
import phi.ap.model.enums.SeedTypes;

public class Sapling extends Producer {
    private SaplingTypes saplingType;

    public Sapling(int height, int width, SaplingTypes saplingType) {
        super(height, width);
        this.saplingType = saplingType;
    }
    @Override
    public void produce() {

    }

    @Override
    public void doTask() {

    }
}
