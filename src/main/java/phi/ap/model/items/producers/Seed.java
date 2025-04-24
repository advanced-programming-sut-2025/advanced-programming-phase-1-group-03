package phi.ap.model.items.producers;

import phi.ap.model.enums.SeedTypes;

public class Seed extends Producer {

    private SeedTypes seedType;
    public Seed(SeedTypes seedType) {
        this.seedType = seedType;
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
