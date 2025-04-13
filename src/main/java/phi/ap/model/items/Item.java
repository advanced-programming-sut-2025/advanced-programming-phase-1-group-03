package phi.ap.model.items;

import phi.ap.model.Coordinate;

public abstract class Item {
    private Coordinate coordinate = null;
    private String name = null;
    private int maxStackSize;
    public abstract void doTask();

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

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
