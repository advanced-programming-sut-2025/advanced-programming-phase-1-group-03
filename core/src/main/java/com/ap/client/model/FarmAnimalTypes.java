package com.ap.client.model;

public enum FarmAnimalTypes {
    BlueChicken("blue_chicken",
            "blue chicken",
            false,
            false,
            false,
            true),
    BrownChicken("brown_chicken",
            "brown chicken",
            false,
            false,
            false,
            true),
    WhiteChicken("white_chicken",
            "white chicken",
            false,
            false,
            false,
            true),
    VoidChicken("void_chicken",
            "void chicken",
            false,
            false,
            false,
            true),
    GoldenChicken("golden_chicken",
            "golden chicken",
            false,
            false,
            false,
            true),
    Dinosaur("dinosaur",
            "dinosaur",
            false,
            false,
            false,
            true),
    Duck("duck",
            "duck",
            false,
            true,
            false,
            true),
    Rabbit("rabbit",
            "rabbit",
            false,
            false,
            false,
            true),

    BrownCow("brown_cow",
            "brown cow",
            false,
            false,
            true,
            false),
    WhiteCow("white_cow",
            "white cow",
            false,
            false,
            true,
            false),
    Sheep("sheep",
            "sheep",
            false,
            false,
            true,
            false),
    Goat("goat",
            "goat",
            false,
            false,
            true,
            false),
    Pig("pig",
            "pig",
            false,
            false,
            true,
            false),

    Ostrich("ostrich",
            "ostrich",
            false,
            false,
            true,
            false),

    BabyBlueChicken("baby/blue_chicken",
            "baby blue chicken",
            true,
            false,
            false,
            true),
    BabyBrownChicken("baby/brown_chicken",
            "baby brown chicken",
            true,
            false,
            false,
            true),
    BabyWhiteChicken("baby/white_chicken",
            "baby white chicken",
            true,
            false,
            false,
            true),
    BabyVoidChicken("baby/void_chicken",
            "baby void chicken",
            true,
            false,
            false,
            true),
    BabyGoldenChicken("baby/golden_chicken",
            "baby golden chicken",
            true,
            false,
            false,
            true),
    BabyRabbit("baby/rabbit",
            "baby rabbit",
            true,
            false,
            false,
            true),

    BabyBrownCow("baby/brown_cow",
            "baby brown cow",
            true,
            false,
            true,
            false),
    BabyWhiteCow("baby/white_cow",
            "baby white cow",
            true,
            false,
            true,
            false),
    BabySheep("baby/sheep",
            "baby sheep",
            true,
            false,
            true,
            false),
    BabyGoat("baby/goat",
            "baby goat",
            true,
            false,
            true,
            false),
    BabyPig("baby/pig",
            "baby pig",
            true,
            false,
            true,
            false),

    BabyOstrich("baby/ostrich",
            "baby ostrich",
            true,
            false,
            true,
            false),
    ;

    private String atlasKey;
    private String uiName;
    private boolean isBaby;
    private boolean canSwim;
    private boolean liveBarn;
    private boolean liveCoop;

    FarmAnimalTypes(String atlasKey, String uiName, boolean isBaby, boolean canSwim, boolean liveBarn, boolean liveCoop) {
        this.atlasKey = atlasKey;
        this.uiName = uiName;
        this.isBaby = isBaby;
        this.canSwim = canSwim;
        this.liveBarn = liveBarn;
        this.liveCoop = liveCoop;
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
}
