package phi.ap.model.items.products;

import phi.ap.model.ItemStack;

import java.util.ArrayList;

public class Recipe extends Product {
    private final ArrayList<ItemStack> ingredients;
    private final ItemStack result;

    public Recipe(int height, int width, ArrayList<ItemStack> ingredients, ItemStack result) {
        super(height, width);
        this.ingredients = ingredients;
        this.result = result;
    }

    public ArrayList<ItemStack> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public void doTask() {

    }
}
