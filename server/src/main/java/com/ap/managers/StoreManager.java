package com.ap.managers;

import com.ap.Server;
import com.ap.component.TwinEntity;
import com.ap.items.EntityFactory;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.animal.AnimalHouse;
import com.ap.model.BarnsType;
import com.ap.model.Menus;
import com.ap.model.ServerPlayer;
import com.ap.model.StoreProduct;
import com.ap.model.store.CarpenterShop;
import com.ap.model.store.MarniesRanchProducts;
import com.ap.model.store.StardropSaloonProducts;
import com.ap.responses.BuyItemResponse;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class StoreManager {

    public StoreManager() {
    }

    public BuyItemResponse buy(ServerPlayer player, String name, Menus menu) {
        var result = switch (menu) {
            case StardropSaloonMenu -> buyStardropSaloon(player, name);
            case CarpenterShopMenu -> buyCarpenterShop(player, name);
            case MarniesRanchMenu -> buyMarniesRanch(player, name);
        };
        return result;
    }


    private BuyItemResponse buyCarpenterShop(ServerPlayer player, String name) {
        var product = CarpenterShop.valueOf(name);
        if (player.gold < product.getPrice()) {
            return new BuyItemResponse(false, "You don't have enough money");
        }

        player.advanceGold(-product.getPrice());

        var inventory = player.playerManager.getInventory();

        if (product.equals(CarpenterShop.Wood) || product.equals(CarpenterShop.Stone)) {
            Item item = product.name().equals("Wood") ? ItemFactory.instance.CreateWood() :
                    ItemFactory.instance.CreateStone();
            inventory.addItem(item, 10);
            return new BuyItemResponse(true, "");
        }

        if (product.equals(CarpenterShop.Well)) {
            Entity entity = EntityFactory.instance.CreateCarrierWellEntity();
            Helper.addEntity(entity, player.playerManager.getFarmEngine());
            return new BuyItemResponse(true, "");
        }
        if (product.equals(CarpenterShop.ShippingBin)) {
            return new BuyItemResponse(true, "");
        }

        BarnsType barnType = BarnsType.valueOf(product.name());
        Entity entity = EntityFactory.instance.CreateCarrierBarnEntity(barnType);
        Helper.addEntity(entity, player.playerManager.getFarmEngine());
        return new BuyItemResponse(true, "");
    }

    private BuyItemResponse buyStardropSaloon(ServerPlayer player, String name) {
        var product = StardropSaloonProducts.valueOf(name);
        if (player.gold < product.getPrice()) {
            return new BuyItemResponse(false, "You don't have enough money");
        }

        player.advanceGold(-product.getPrice());

        var inventory = player.playerManager.getInventory();

        // Food
        if (product.getFood() != null) {
            inventory.addItem(ItemFactory.instance.CreateFood(product.getFood(), product.getPrice()), 1);
        } else { // Recipe
            var recipe = product.getRecipe();
            if (inventory.getFoodRecipes().contains(recipe)) {
                return new BuyItemResponse(false, "You already bought this recipe");
            }
            inventory.getFoodRecipes().add(recipe);
        }
        return new BuyItemResponse(true, "");
    }


    private BuyItemResponse buyMarniesRanch(ServerPlayer player, String name) {
        var product = MarniesRanchProducts.valueOf(name);
        if (player.gold < product.getPrice()) {
            return new BuyItemResponse(false, "You don't have enough money");
        }
        //Animal
        if (product.getAnimalType() != null) {
            TwinEntity twin = new TwinEntity();
            for (AnimalHouse house : player.playerManager.getGameManager().getAnimalManagers().get(player).getHouses()) {
                if (house.isFull()) continue;
                if (house.getType().isBarn() == product.getAnimalType().isLiveCoop()) continue;
                Entity entity = EntityFactory.instance.CreateCarrierFarmAnimalEntity(product.getAnimalType());
                twin.add(entity, house.getMap());
            }

            if (twin.getEntities().isEmpty()) {
                return new BuyItemResponse(false, "There is no proper house with free space for this animal");
            }

            for (int i = 0; i < twin.getEntities().size(); i++) {
                Entity entity = twin.getEntities().get(i);
                entity.add(twin);
            }

            for (int i = 0; i < twin.getEntities().size(); i++) {
                Entity entity = twin.getEntities().get(i);
                Helper.addEntity(entity, twin.getMaps().get(i).getEngine());
                System.out.println("entity: " + entity + "added to engine: " + twin.getMaps().get(i).getEngine() + "," + twin.getMaps().get(i).getMapAsset());
            }
            player.advanceGold(-product.getPrice());
            return new BuyItemResponse(true, "");

        }

        //Hay
        if (product.getName().equals("Hay")) {
            player.playerManager.getInventory().addItem(ItemFactory.instance.CreateHay(), 16);
            player.advanceGold(-product.getPrice());
            return new BuyItemResponse(true, "");
        }

        return new BuyItemResponse(false, "item does not exist");

    }

}
