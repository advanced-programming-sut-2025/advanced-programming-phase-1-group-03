package phi.ap.model.items.buildings;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.StoreTypes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Store extends Building {
    public Store(int height, int width) {
        super(height, width);
    }

    StoreTypes storeTypes;
    private ArrayList<ItemStack> availableProducts;

    @Override
    public void doTask() {

    }
}
