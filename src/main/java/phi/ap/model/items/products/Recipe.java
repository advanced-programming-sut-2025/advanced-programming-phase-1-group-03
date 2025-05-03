package phi.ap.model.items.products;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.RecipeTypes;

import java.util.ArrayList;

public class Recipe extends Product {
    private final ArrayList<ItemStack> ingredients;
    private final ItemStack result;
    private final RecipeTypes recipeType;
    public Recipe(int height, int width, ArrayList<ItemStack> ingredients, ItemStack result, RecipeTypes recipeType) {
        super(height, width);
        this.ingredients = ingredients;
        this.result = result;
        this.recipeType = recipeType;
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
