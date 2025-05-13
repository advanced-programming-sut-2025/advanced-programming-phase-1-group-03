package phi.ap.model.items.buildings;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.enums.TileType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Store extends Building {
    private StoreTypes storeTypes;
    private ArrayList<ItemStack> availableProducts;

    public Store(int height, int width, StoreTypes storeType) {
        super(height, width);
        setName(storeType.getName());
        fillTile(storeType.getTileType().getTile());
        setWalls();
        this.storeTypes = storeType;
    }


    @Override
    public void doTask() {

    }
}
