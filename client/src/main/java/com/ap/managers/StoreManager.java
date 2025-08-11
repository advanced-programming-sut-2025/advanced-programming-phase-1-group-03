package com.ap.managers;

import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.EntityFactory;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.model.BarnsType;
import com.ap.model.GameData;
import com.ap.model.Menus;
import com.ap.model.store.CarpenterShop;
import com.ap.model.store.StardropSaloonProducts;
import com.ap.ui.widget.StoreMenu;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

public class StoreManager {
    private Inventory inventory;
    private AudioService audioService;
    private Engine farmEngine;
    private World world;

    public StoreManager(Inventory inventory, AudioService audioService, Engine farmEngine, World world) {
        this.inventory = inventory;
        this.audioService = audioService;
        this.farmEngine = farmEngine;
        this.world = world;
    }

    public void onBuy(StoreMenu.StoreProduct storeProduct, Menus menu) {
        boolean isSuccess = false;
        isSuccess = switch (menu) {
            case StardropSaloonMenu -> buyStardropSaloon(storeProduct);
            case CarpenterShopMenu -> buyCarpenterShop(storeProduct);
        };
        if(isSuccess) {
            GameData.getInstance().setPlayerGold(GameData.getInstance().getPlayerGold() - storeProduct.sellPrice);
            audioService.playSound(SoundAsset.Purchase);
        }
    }

    private boolean buyCarpenterShop(StoreMenu.StoreProduct storeProduct) {
        var product = CarpenterShop.valueOf(storeProduct.enumName);
        if(GameData.getInstance().getPlayerGold() < storeProduct.sellPrice) {
            GameUIManager.instance.showMessageDialog("You don't have enough money!");
            return false;
        }

        if(product.equals(CarpenterShop.Wood) || product.equals(CarpenterShop.Stone)) {
        //    Item item = product.name().equals("Wood") ? ItemFactory.instance.CreateWood() :
        //            ItemFactory.instance.CreateStone();
      //      inventory.addItem(item, 10);
            return true;
        }

        if(product.equals(CarpenterShop.Well)) {
            Entity entity = EntityFactory.instance.CreateCarrierWellEntity();
            farmEngine.addEntity(entity);
            return true;
        }
        if(product.equals(CarpenterShop.ShippingBin)) {
            return true;
        }

        BarnsType barnType = BarnsType.valueOf(product.name());
        Entity entity = EntityFactory.instance.CreateCarrierBarnEntity(barnType);
        farmEngine.addEntity(entity);
        return true;
    }

    private boolean buyStardropSaloon(StoreMenu.StoreProduct storeProduct) {
        var product = StardropSaloonProducts.valueOf(storeProduct.enumName);
        if(GameData.getInstance().getPlayerGold() < storeProduct.sellPrice) {
            GameUIManager.instance.showMessageDialog("You don't have enough money!");
            return false;
        }
        // Food
        if(product.getFood() != null) {
  //          inventory.addItem(ItemFactory.instance.CreateFood(product.getFood(), storeProduct.sellPrice), 1);
        } else { // Recipe
            var recipe = product.getRecipe();
 //           if(inventory.getFoodRecipes().contains(recipe)) {
    //            GameUIManager.instance.showMessageDialog("You already bought this recipe!");
   //             return false;
   //         }
   //         inventory.getFoodRecipes().add(recipe);
        }
        return true;
    }
}
