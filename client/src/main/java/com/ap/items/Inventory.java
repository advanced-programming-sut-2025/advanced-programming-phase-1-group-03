package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.model.FoodRecipes;
import com.ap.model.NetworkItemStack;
import com.ap.notifiers.InventoryNotifier;

import java.util.ArrayList;

public class Inventory {
    public final static int maxTrashCanLevel = 4;
    
    
    private final ArrayList<ItemStack> items = new ArrayList<>();
    private final ArrayList<FoodRecipes> foodRecipes = new ArrayList<>();
    private final AssetService assetService;
    
    public int storage = 12;
    
    public Inventory(AssetService assetService) {
        this.assetService = assetService;
    }

    public void load(InventoryNotifier inventoryNotifier) {
        this.storage = inventoryNotifier.storage;
        items.clear();
        int index = 0;
        for(NetworkItemStack networkItemStack : inventoryNotifier.items) {
            var icon = assetService.get(networkItemStack.atlasAsset).findRegion(networkItemStack.atlasKey);
            Item item = new Item(networkItemStack.name, icon, networkItemStack.atlasKey, networkItemStack.atlasAsset, index++);
            items.add(new ItemStack(item, networkItemStack.amount));
        }
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    public int getTrashCanLevel() {
        return 0;
    }

    public int getMaxSize() {
        return storage;
    }

}
