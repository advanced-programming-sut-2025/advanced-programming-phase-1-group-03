package phi.ap.model.items;

import phi.ap.model.Coordinate;
import phi.ap.model.Ground;

public abstract class Item extends Ground {
    private String name = null;
    private int maxStackSize;

    public Item(int height, int width) {
        super(height, width);
    }
    public Item() {
        super(1, 1);
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
}
