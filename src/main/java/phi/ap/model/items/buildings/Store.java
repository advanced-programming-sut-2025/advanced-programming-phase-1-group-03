package phi.ap.model.items.buildings;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.StoreFeatures;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Store extends Building {
    StoreFeatures storeFeatures;
    private ArrayList<ItemStack> availableProducts;

    @Override
    public void doTask() {

    }
}
