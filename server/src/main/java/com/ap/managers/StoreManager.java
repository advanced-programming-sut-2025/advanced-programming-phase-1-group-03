package com.ap.managers;

import com.ap.Server;
import com.ap.items.EntityFactory;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.model.BarnsType;
import com.ap.model.Menus;
import com.ap.model.ServerPlayer;
import com.ap.model.store.CarpenterShop;
import com.ap.model.store.StardropSaloonProducts;
import com.ap.responses.BuyItemResponse;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Entity;

public class StoreManager {

    public StoreManager() {
    }

    public BuyItemResponse buy(ServerPlayer player, String name, Menus menu) {
        var result = switch (menu) {
            case StardropSaloonMenu -> buyStardropSaloon(player, name);
            case CarpenterShopMenu -> buyCarpenterShop(player, name);
        //    case MarniesRanchMenu -> buyMarniesRanch(storeProduct);
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


//    private boolean buyMarniesRanch(StoreMenu.StoreProduct storeProduct) {
//        var product = MarniesRanchProducts.valueOf(storeProduct.enumName);
//        if(GameData.getInstance().getPlayerGold() < storeProduct.sellPrice) {
//            GameUIManager.instance.showMessageDialog("You don't have enough money!");
//            return false;
//        }
//        //Animal
//        if (product.getAnimalType() != null) {
//            TwinEntity twin = new TwinEntity();
//            for (AnimalHouse house : AnimalManager.instance.getHouses()) {
//                if (house.isFull()) continue;
//                if (house.getType().isBarn() == product.getAnimalType().isLiveCoop()) continue;
//                Entity entity = EntityFactory.instance.CreateCarrierFarmAnimalEntity(product.getAnimalType());
//                Engine engine = gameScreen.getBarnEngine(house.getType());
//                twin.add(entity, engine);
//            }
//
//            for (int i = 0; i < twin.getEngines().size(); i++) {
//                Engine engine = twin.getEngines().get(i);
//                Entity entity = twin.getEntities().get(i);
//                engine.addEntity(entity);
//                entity.add(twin);
//            }
//
//        }
//
//        //Hay
//        if (product.getName().equals("Hay")) {
//            inventory.addItem(ItemFactory.instance.CreateHay(), 10);
//        }
//        return true;
//    }
}
