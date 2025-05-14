package phi.ap.model.items;

import phi.ap.model.enums.SaplingTypes;

public class Sapling extends Item {
    private SaplingTypes saplingType;

    public Sapling(int height, int width, SaplingTypes saplingType) {
        super(height, width);
        this.saplingType = saplingType;
        setName(saplingType.toString());
    }

    @Override
    public void doTask() {

    }
    public boolean canStackWith(Item item) {
        if (!super.canStackWith(item)) return false;
        if (item instanceof Sapling sapling) {
            return saplingType == sapling.saplingType;
        }
        return false;
    }

}

