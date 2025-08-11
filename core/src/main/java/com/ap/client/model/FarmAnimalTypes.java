package com.ap.client.model;

import java.util.ArrayList;
import java.util.Arrays;

public enum FarmAnimalTypes {
    BlueChicken("blue_chicken",
            "blue chicken",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.Egg,
            AnimalProducts.LargeEgg),
    BrownChicken("brown_chicken",
            "brown chicken",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.BrownEgg,
            AnimalProducts.LargeBrownEgg),
    WhiteChicken("white_chicken",
            "white chicken",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.Egg,
            AnimalProducts.LargeEgg),
    VoidChicken("void_chicken",
            "void chicken",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.VoidEgg),
    GoldenChicken("golden_chicken",
            "golden chicken",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.GoldenEgg),
    Dinosaur("dinosaur",
            "dinosaur",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.DinosaurEgg),
    Duck("duck",
            "duck",
            16, 16, false,
            true,
            false,
            true,
            AnimalProducts.DuckEgg,
            AnimalProducts.DuckFeather),
    Rabbit("rabbit",
            "rabbit",
            16, 16, false,
            false,
            false,
            true,
            AnimalProducts.RabbitFoot,
            AnimalProducts.Wool),

    BrownCow("brown_cow",
            "brown cow",
            32, 32, false,
            false,
            true,
            false,
            AnimalProducts.Milk,
            AnimalProducts.LargeMilk),
    WhiteCow("white_cow",
            "white cow",
            32, 32, false,
            false,
            true,
            false,
            AnimalProducts.Milk,
            AnimalProducts.LargeMilk),
    Sheep("sheep",
            "sheep",
            32, 32, false,
            false,
            true,
            false,
            AnimalProducts.Wool),
    ShearedSheep("sheared_sheep",
            "sheared sheep",
            32, 32, false,
            false,
            true,
            false),
    Goat("goat",
            "goat",
            32, 32, false,
            false,
            true,
            false,
            AnimalProducts.GoatMilk,
            AnimalProducts.LargeGoatMilk),
    Pig("pig",
            "pig",
            32, 32, false,
            false,
            true,
            false,
            AnimalProducts.Truffle),

    Ostrich("ostrich",
            "ostrich",
            32, 32, false,
            false,
            true,
            false,
            AnimalProducts.OstrichEgg),

    BabyBlueChicken("baby/blue_chicken",
            "baby blue chicken",
            16, 16, true,
            false,
            false,
            true),
    BabyBrownChicken("baby/brown_chicken",
            "baby brown chicken",
            16, 16, true,
            false,
            false,
            true),
    BabyWhiteChicken("baby/white_chicken",
            "baby white chicken",
            16, 16, true,
            false,
            false,
            true),
    BabyVoidChicken("baby/void_chicken",
            "baby void chicken",
            16, 16, true,
            false,
            false,
            true),
    BabyGoldenChicken("baby/golden_chicken",
            "baby golden chicken",
            16, 16, true,
            false,
            false,
            true),
    BabyRabbit("baby/rabbit",
            "baby rabbit",
            16, 16, true,
            false,
            false,
            true),

    BabyBrownCow("baby/brown_cow",
            "baby brown cow",
            32, 32, true,
            false,
            true,
            false),
    BabyWhiteCow("baby/white_cow",
            "baby white cow",
            32, 32, true,
            false,
            true,
            false),
    BabySheep("baby/sheep",
            "baby sheep",
            32, 32, true,
            false,
            true,
            false),
    BabyGoat("baby/goat",
            "baby goat",
            32, 32, true,
            false,
            true,
            false),
    BabyPig("baby/pig",
            "baby pig",
            32, 32, true,
            false,
            true,
            false),

    BabyOstrich("baby/ostrich",
            "baby ostrich",
            32, 32, true,
            false,
            true,
            false),

    ;

    private final String atlasKey;
    private final String uiName;
    private final float widthInPx;
    private final float heightInPx;
    private final boolean isBaby;
    private final boolean canSwim;
    private final boolean liveBarn;
    private final boolean liveCoop;
    private ArrayList<AnimalProducts> products;

    FarmAnimalTypes(String atlasKey, String uiName, float widthInPx, float heightInPx, boolean isBaby, boolean canSwim,
                    boolean liveBarn, boolean liveCoop, AnimalProducts ... animalProducts) {
        this.atlasKey = atlasKey;
        this.uiName = uiName;
        this.widthInPx = widthInPx;
        this.heightInPx = heightInPx;
        this.isBaby = isBaby;
        this.canSwim = canSwim;
        this.liveBarn = liveBarn;
        this.liveCoop = liveCoop;
        this.products = new ArrayList<>();
        this.products.addAll(Arrays.asList(animalProducts));
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public String getUiName() {
        return uiName;
    }

    public boolean isBaby() {
        return isBaby;
    }

    public boolean isCanSwim() {
        return canSwim;
    }

    public boolean isLiveBarn() {
        return liveBarn;
    }

    public boolean isLiveCoop() {
        return liveCoop;
    }

    public float getWidthInPx() {
        return widthInPx;
    }

    public float getHeightInPx() {
        return heightInPx;
    }
}
