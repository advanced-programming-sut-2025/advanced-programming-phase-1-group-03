package phi.ap.model.items;

import phi.ap.model.Eatable;
import phi.ap.model.Ground;

public abstract class Item extends Ground {
    private String name = null;
    private int maxStackSize = 64;
    private boolean isRemovableByPickaxe = false;
    private int sellPrice = 0;
    private boolean isSellable = false;
    private Eatable eatable = null;

    public Item(int height, int width) {
        super(height, width);
        setName("Item");
    }

    public Item() {
        super(1, 1);
        setName("Item");
    }

    public abstract void doTask();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public boolean canStackWith(Item otherItem) {
        return otherItem.getName().equals(this.getName());
    }

    public boolean isRemovableByPickaxe() {
        return isRemovableByPickaxe;
    }
    public void makeRemovableByPickaxe() {
        isRemovableByPickaxe = true;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public boolean isSellable() {
        return isSellable;
    }

    public Eatable getEatable() {
        return eatable;
    }

    public void setEatable(Eatable eatable) {
        this.eatable = eatable;
    }
}


