package phi.ap.model.items.buildings;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.StoreTypes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Store extends Building {
    private StoreTypes storeType;
    public Store(int height, int width, StoreTypes storeType) {
        super(height, width);
        setName(storeType.getName());
        this.storeType = storeType;
    }

    StoreTypes storeTypes;
    private ArrayList<ItemStack> availableProducts;

    @Override
    public void doTask() {

    }
}
