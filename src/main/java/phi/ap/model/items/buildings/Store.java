package phi.ap.model.items.buildings;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.StoreTypes;

import java.util.ArrayList;

public class Store extends Building {
    StoreTypes storeTypes;
    private ArrayList<ItemStack> availableProducts;

    @Override
    public void doTask() {

    }
}
