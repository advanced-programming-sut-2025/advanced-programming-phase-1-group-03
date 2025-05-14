package phi.ap.model.items;

import phi.ap.model.enums.TileType;

public class Water extends Item {
    private int capacity = Integer.MAX_VALUE;

    public Water(){
        super();
        setName("Water");
        setMaxStackSize(0);
        fillTile(TileType.Water.getTile());
    }
    @Override
    public void doTask() {

    }

    public int getCapacity() {
        return capacity;
    }
    public void reduceCapacity(int amount) {
        capacity = capacity - amount;
        if(capacity == 0)
            getFather().removeItem(this);
    }

}
