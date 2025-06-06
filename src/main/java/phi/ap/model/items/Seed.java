package phi.ap.model.items;

import phi.ap.model.enums.SeedTypes;

public class Seed extends Item {

    private SeedTypes seedType;


    public Seed(int height, int width, SeedTypes seedType) {
        super(height, width);
        this.seedType = seedType;
        fillTile(seedType.getTile());
        setSellable(false);
        setName(seedType.name());
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
    public boolean canStackWith(Item item) {
        if (!super.canStackWith(item)) return false;
        if (item instanceof Seed seed) {
            return seedType == seed.seedType;
        }
        return false;
    }
}
