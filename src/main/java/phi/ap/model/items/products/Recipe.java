package phi.ap.model.items.products;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.RecipeTypes;

import java.util.ArrayList;

public class Recipe extends Product {
    private final ArrayList<ItemStack> ingredients;
    private final ItemStack result;
    private final RecipeTypes recipeType;
    public Recipe(ArrayList<ItemStack> ingredients, ItemStack result, RecipeTypes recipeType) {
        super(1, 1);
        this.ingredients = ingredients;
        this.result = result;
        this.recipeType = recipeType;
        setName(recipeType.getName());
    }

    public ArrayList<ItemStack> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public RecipeTypes getRecipeType() {
        return this.recipeType;
    }

    @Override
    public void doTask() {

    }
}
