package phi.ap.model.items.products;

import phi.ap.model.ItemStack;

import java.util.ArrayList;

public class Recipe extends Product {
    private final ArrayList<ItemStack> ingredients;
    private final ItemStack result;

    public Recipe(ArrayList<ItemStack> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public ArrayList<ItemStack> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }
}
