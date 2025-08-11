package com.ap.client.managers;

import com.ap.client.asset.SoundAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.component.TwinEntity;
import com.ap.client.component.items.Barn;
import com.ap.client.items.Animals.AnimalHouse;
import com.ap.client.items.EntityFactory;
import com.ap.client.items.Inventory;
import com.ap.client.items.Item;
import com.ap.client.items.ItemFactory;
import com.ap.client.model.BarnsType;
import com.ap.client.model.GameData;
import com.ap.client.model.Menus;
import com.ap.client.model.store.CarpenterShop;
import com.ap.client.model.store.MarniesRanchProducts;
import com.ap.client.model.store.StardropSaloonProducts;
import com.ap.client.screen.GameScreen;
import com.ap.client.ui.widget.StoreMenu;
import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class StoreManager {
    private Inventory inventory;
    private AudioService audioService;
    private Engine farmEngine;
    private World world;
    private GameScreen gameScreen;

    public StoreManager(Inventory inventory, AudioService audioService, Engine farmEngine, World world, GameScreen gameScreen) {
        this.inventory = inventory;
        this.audioService = audioService;
        this.farmEngine = farmEngine;
        this.world = world;
        this.gameScreen = gameScreen;
    }

    public void onBuy(StoreMenu.StoreProduct storeProduct, Menus menu) {
        boolean isSuccess = false;
        isSuccess = switch (menu) {
            case StardropSaloonMenu -> buyStardropSaloon(storeProduct);
            case CarpenterShopMenu -> buyCarpenterShop(storeProduct);
            case MarniesRanchMenu -> buyMarniesRanch(storeProduct);
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
            Item item = product.name().equals("Wood") ? ItemFactory.instance.CreateWood() :
                    ItemFactory.instance.CreateStone();
            inventory.addItem(item, 10);
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
            inventory.addItem(ItemFactory.instance.CreateFood(product.getFood(), storeProduct.sellPrice), 1);
        } else { // Recipe
            var recipe = product.getRecipe();
            if(inventory.getFoodRecipes().contains(recipe)) {
                GameUIManager.instance.showMessageDialog("You already bought this recipe!");
                return false;
            }
            inventory.getFoodRecipes().add(recipe);
        }
        return true;
    }

    private boolean buyMarniesRanch(StoreMenu.StoreProduct storeProduct) {
        var product = MarniesRanchProducts.valueOf(storeProduct.enumName);
        if(GameData.getInstance().getPlayerGold() < storeProduct.sellPrice) {
            GameUIManager.instance.showMessageDialog("You don't have enough money!");
            return false;
        }
        //Animal
        if (product.getAnimalType() != null) {
            TwinEntity twin = new TwinEntity();
            for (AnimalHouse house : AnimalManager.instance.getHouses()) {
                if (house.isFull()) continue;
                if (house.getType().isBarn() == product.getAnimalType().isLiveCoop()) continue;
                Entity entity = EntityFactory.instance.CreateCarrierFarmAnimalEntity(product.getAnimalType());
                Engine engine = gameScreen.getBarnEngine(house.getType());
                twin.add(entity, engine);
            }

            for (int i = 0; i < twin.getEngines().size(); i++) {
                Engine engine = twin.getEngines().get(i);
                Entity entity = twin.getEntities().get(i);
                engine.addEntity(entity);
                entity.add(twin);
            }

        }

        //Hay
        if (product.getName().equals("Hay")) {
            inventory.addItem(ItemFactory.instance.CreateHay(), 10);
        }
        return true;
    }

}
