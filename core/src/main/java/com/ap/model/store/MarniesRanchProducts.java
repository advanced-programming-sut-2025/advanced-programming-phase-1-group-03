package com.ap.model.store;



import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.model.FarmAnimalTypes;
import com.ap.model.StoreProduct;

import java.util.ArrayList;

public enum MarniesRanchProducts {
    //    MilkPay("Milk Pail", "Gather milk from your animals.",	1000,
//            1, new MilkPail(), StoreTypes.MarnieRanch, null),
//    Shear("Shears", "Use this to collect wool from sheep", 	1000,
//            1, new Shear(), StoreTypes.MarnieRanch, null),
    Hay("Hay",
            "Dried grass used as animal food.",
            50),
    // Animals
    Chicken("Chicken",
            "Well cared-for chickens lay eggs every day. Lives in the coop.",
            800,
            FarmAnimalTypes.WhiteChicken),
    Cow("Cow",
            "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn.",
            1500,
            FarmAnimalTypes.WhiteCow),
    Goat("Goat",
            "Happy provide goat milk every other day. A milk pail is required to harvest the milk." +
                    " Lives in the barn.",
            4000,
            FarmAnimalTypes.Goat),
    Duck("Duck",
            "Happy lay duck eggs every other day. Lives in the coop.",
            1200,
            FarmAnimalTypes.Duck),
    Sheep("Sheep",
            "Can be shorn for wool. A pair of shears is required to harvest the wool. " +
                    "Lives in the barn.",
            8000,
            FarmAnimalTypes.Sheep),
    Rabbit("Rabbit",
            "These are wooly rabbits! They shed precious wool every few days. Lives " +
                    "in the coop.",
            8000,
            FarmAnimalTypes.Rabbit),
    Pig("Pig",
            "These pigs are trained to find truffles! Lives in the barn.",
            16000,
            FarmAnimalTypes.Pig),
//    ,Dinosaur("Dinosaur",
//                     "The Dinosaur is a farm animal that lives in a Big Coop.",
//                     14000),
    ;

    private final String name;
    private final String description;
    private final int price;
    private FarmAnimalTypes animalType;

    MarniesRanchProducts(String name, String description, int price, FarmAnimalTypes animalType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.animalType = animalType;
    }

    MarniesRanchProducts(String name, String description, int price) {
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

    public FarmAnimalTypes getAnimalType() {
        return animalType;
    }


    public static ArrayList<StoreProduct> buildStoreItems(AssetService assetService) {
        ArrayList<StoreProduct> list = new ArrayList<>();
        int row = 0;
        for(MarniesRanchProducts product : MarniesRanchProducts.values()) {
            if (product.getName().equals("Hay")) {
                var texture = assetService.get(AtlasAsset.AnimalProducts).findRegion("Hay");
                list.add(new StoreProduct(texture, product.getName(), product.name(), product.description, product.getPrice(), row++));
                continue;
            }
            String key = "shop_icon/" + product.name().toLowerCase();
            var texture = assetService.get(AtlasAsset.Animals).findRegion(key);
            list.add(new StoreProduct(texture, product.getName(), product.name(), product.description, product.getPrice(), row++));
        }
        return list;
    }
}
