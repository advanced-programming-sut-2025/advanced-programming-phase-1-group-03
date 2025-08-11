package com.ap.client.model.store;

import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.ui.widget.StoreMenu;

import java.util.ArrayList;

public enum CarpenterShop {
    Wood("Wood 10x", "A sturdy, yet flexible plant material with a wide variety of uses.", 100),
    Stone("Stone 10x", "A sturdy, yet flexible plant material with a wide variety of uses.", 200) ,
    Barn("Barn", "Houses 4 barn-dwelling animals.", 6000),
    BigBarn("Big Barn", "Houses 8 barn-dwelling animals. Unlocks goats.", 12000),
    DeluxeBarn("Deluxe Barn", "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.", 25000),
    Coop("Coop", "Houses 4 coop-dwelling animals.", 4000),
    BigCoop("Big Coop", "Houses 8 coop-dwelling animals. Unlocks ducks.", 10000),
    DeluxeCoop("Deluxe Coop", "Houses 12 coop-dwelling animals. Unlocks rabbits.", 20000),
    Well("Well", "Provides a place for you to refill your watering can.", 1000),
    ShippingBin("Shipping Bin", "Items placed in it will be included in the nightly shipment.", 250);

    private final String name;
    private final String description;
    private int price;

    CarpenterShop(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public static ArrayList<StoreMenu.StoreProduct> buildStoreItems(AssetService assetService) {
        ArrayList<StoreMenu.StoreProduct> list = new ArrayList<>();
        int row = 0;
        for(CarpenterShop product : CarpenterShop.values()) {
            String key = product.name();
            var texture = assetService.get(AtlasAsset.Carpenter).findRegion(key);
            double rand = Math.random();
            rand = 0;
            System.out.println(product.getName() + " " + rand);
            if(rand <= 0.5)
                list.add(new StoreMenu.StoreProduct(texture, product.getName(), product.name(), product.description, product.getPrice(), row++, true));
            else
                list.add(new StoreMenu.StoreProduct(texture, product.getName(), product.name(), product.description, product.getPrice(), row++, false));        }
        return list;
    }

}
