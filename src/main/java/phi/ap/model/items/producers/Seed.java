package phi.ap.model.items.producers;

import phi.ap.model.enums.SeedTypes;

public class Seed extends Producer {

    private SeedTypes seedType;

    public Seed(int height, int width, SeedTypes seedType) {
        super(height, width);
        this.seedType = seedType;
        fillTile(seedType.getTile());
    }

    @Override
    public void produce() {

    }

    @Override
    public void doTask() {

    }

    public SeedTypes getSeedType() {
        return seedType;
    }

    public void setSeedType(SeedTypes seedType) {
        this.seedType = seedType;
    }
}
