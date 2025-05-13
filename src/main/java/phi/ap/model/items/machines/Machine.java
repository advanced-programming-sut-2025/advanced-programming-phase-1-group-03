package phi.ap.model.items.machines;

import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.items.Item;

public abstract class Machine extends Item {
    private CraftingTypes craftingType = null;
    private String machineName;
    protected Machine(int height, int width, CraftingTypes craftingType) {
        super(height, width);
        this.craftingType = craftingType;
        setName(craftingType.getName());
        setMaxStackSize(1);
        setSellPrice(craftingType.getSellPrice());
        if(craftingType.getSellPrice() == null)
            setSellable(false);
        else {
            setSellable(true);
            setSellPrice(craftingType.getSellPrice());
        }
    }
    protected Machine(int height, int width, String machineName, Integer price) {
        super(height, width);
        this.machineName = machineName;
        setName(machineName);
        setMaxStackSize(1);
        if(price == null)
            setSellable(false);
        else {
            setSellable(true);
            setSellPrice(price);
        }
    }
    public void setCraftingType(String name) {
        this.craftingType = CraftingTypes.valueOf(name);
    }
    public CraftingTypes getCraftingType() {
        if(craftingType == null)
            craftingType = CraftingTypes.valueOf(machineName);
        return this.craftingType;
    }
}
