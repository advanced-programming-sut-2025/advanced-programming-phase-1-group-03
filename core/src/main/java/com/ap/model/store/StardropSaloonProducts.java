package com.ap.model.store;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
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
    Coffee("Coffee", "It smells delicious. This is sure to give you a boost.", 300, Foods.Coffee)
    ;

    private final String name;
    private final String description;
    private final int price;
    private final Foods food;

    StardropSaloonProducts(String name, String description, int price, Foods food) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.food = food;
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
            var texture = assetService.get(AtlasAsset.Foods).findRegion(product.name());
            list.add(new StoreMenu.StoreProduct(texture, product.getName(), product.getPrice(), row++));
        }
        return list;
    }
}
