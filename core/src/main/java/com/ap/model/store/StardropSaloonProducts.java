package com.ap.model.store;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.model.FoodRecipes;
import com.ap.model.Foods;
import com.ap.ui.widget.StoreMenu;

import java.util.ArrayList;
import java.util.List;

public enum StardropSaloonProducts {
    Beer("Beer", "Drink in moderation.", 400, Foods.Beer),
    Salad("Salad", "A healthy garden salad.", 220, Foods.Salad),
    Bread("Bread", "A crusty baguette.", 120, Foods.Bread),
    Spaghetti("Spaghetti", "An old favorite.", 240, Foods.Spaghetti),
    Pizza("Pizza", "It's popular for all the right reasons.", 600, Foods.Pizza),
    Coffee("Coffee", "It smells delicious. This is sure to give you a boost.", 300, Foods.Coffee),

    HashbrownsRecipe("Hashbrowns Recipe", "A recipe to make Hashbrowns", 50, FoodRecipes.Hashbrowns),
    OmeletRecipe("Omelet Recipe", "A recipe to make Omelet", 100, FoodRecipes.Omelet),
    PancakesRecipe("Pancakes Recipe", "A recipe to make Pancakes", 100, FoodRecipes.Pancakes),
    BreadRecipe("Bread Recipe", "A recipe to make Bread", 100, FoodRecipes.Bread),
    TortillaRecipe("Tortilla Recipe", "A recipe to make Tortilla", 100, FoodRecipes.Tortilla),
    PizzaRecipe("Pizza Recipe", "A recipe to make Pizza", 150, FoodRecipes.Pizza),
    Maki_RollRecipe("Maki Roll Recipe", "A recipe to make Maki Roll", 300, FoodRecipes.Makiroll),
    Triple_Shot_EspressoRecipe("Triple Shot Espresso Recipe", "A recipe to make Triple Shot Espresso",
            5000, FoodRecipes.TripleShotEspersso),
    CookieRecipe("Cookie Recipe", "A recipe to make Cookie", 300, FoodRecipes.Cookie);
    ;

    private final String name;
    private final String description;
    private final int price;
    private Foods food = null;
    private FoodRecipes recipe = null;

    StardropSaloonProducts(String name, String description, int price, Foods food) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.food = food;
    }
    StardropSaloonProducts(String name, String description, int price, FoodRecipes recipe) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public Foods getFood() {
        return food;
    }

    public static ArrayList<StoreMenu.StoreProduct> buildStoreItems(AssetService assetService) {
        ArrayList<StoreMenu.StoreProduct> list = new ArrayList<>();
        int row = 0;
        for(StardropSaloonProducts product : StardropSaloonProducts.values()) {
            String key = product.name();
            if(key.endsWith("Recipe")) {
                key = key.replace("Recipe", "");
            }
            var texture = assetService.get(AtlasAsset.Foods).findRegion(key);
            list.add(new StoreMenu.StoreProduct(texture, product.getName(), product.name(), product.description, product.getPrice(), row++));
        }
        return list;
    }

    public FoodRecipes getRecipe() {
        return recipe;
    }
}
