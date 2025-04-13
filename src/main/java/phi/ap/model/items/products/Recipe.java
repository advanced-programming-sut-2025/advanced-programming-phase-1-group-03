package phi.ap.model.items.products;

import phi.ap.model.ItemStack;

import java.util.ArrayList;

public class Recipe extends Product {
    private ArrayList<ItemStack> items;
    public Recipe(ArrayList<ItemStack> items) {
        this.items = items;
    }
}
