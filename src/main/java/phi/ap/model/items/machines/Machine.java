package phi.ap.model.items.machines;

import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.items.Item;

public abstract class Machine extends Item {
    private CraftingTypes craftingType;
    protected Machine(int height, int width) {
        super(height, width);
    }
    public void setCraftingType(String name) {
        this.craftingType = CraftingTypes.valueOf(name);
    }
    public CraftingTypes getCraftingType() {
        return this.craftingType;
    }
}
