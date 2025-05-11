package phi.ap.model.items;

import phi.ap.model.enums.SaplingTypes;

public class Sapling extends Item {
    private SaplingTypes saplingType;

    public Sapling(int height, int width, SaplingTypes saplingType) {
        super(height, width);
        this.saplingType = saplingType;
    }

    @Override
    public void doTask() {

    }
}
