package phi.ap.model.items;

import phi.ap.model.enums.SeedTypes;

public class Seed extends Item {

    private SeedTypes seedType;

    public Seed(int height, int width, SeedTypes seedType) {
        super(height, width);
        this.seedType = seedType;
        fillTile(seedType.getTile());
        setSellable(false);
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
