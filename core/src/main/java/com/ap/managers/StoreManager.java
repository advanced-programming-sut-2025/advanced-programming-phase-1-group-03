package com.ap.managers;

import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.model.GameData;
import com.ap.model.Menus;
import com.ap.model.store.StardropSaloonProducts;
import com.ap.ui.widget.StoreMenu;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.awt.*;

public class StoreManager {
    private Inventory inventory;
    private AudioService audioService;

    public StoreManager(Inventory inventory, AudioService audioService) {
        this.inventory = inventory;
        this.audioService = audioService;
    }

    public void onBuy(StoreMenu.StoreProduct storeProduct, Menus menu) {
        switch (menu) {
            case StardropSaloonMenu -> {
                buyStardropSaloon(storeProduct);
            }
        }
    }

    private void buyStardropSaloon(StoreMenu.StoreProduct storeProduct) {
        var product = StardropSaloonProducts.valueOf(storeProduct.enumName);
        if(GameData.getInstance().getPlayerGold() < storeProduct.sellPrice) {
            GameUIManager.instance.showMessageDialog("You don't have enough money!");
            return;
        }
        // Reducing money
        GameData.getInstance().setPlayerGold(GameData.getInstance().getPlayerGold() - storeProduct.sellPrice);

        // Food
        if(product.getFood() != null) {
            inventory.addItem(ItemFactory.instance.CreateFood(product.getFood(), storeProduct.sellPrice), 1);
        } else { // Recipe
            var recipe = product.getRecipe();
            if(inventory.getFoodRecipes().contains(recipe)) {
                GameUIManager.instance.showMessageDialog("You already bought this recipe!");
                return;
            }
            inventory.getFoodRecipes().add(recipe);
        }
        audioService.playSound(SoundAsset.Purchase);
    }
}
