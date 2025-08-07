package com.ap.client.model;

public enum FarmAnimalTypes {
    BlueChicken("blue_chicken",
            "blue chicken",
            16, 16, false,
            false,
            false,
            true),
    BrownChicken("brown_chicken",
            "brown chicken",
            16, 16, false,
            false,
            false,
            true),
    WhiteChicken("white_chicken",
            "white chicken",
            16, 16, false,
            false,
            false,
            true),
    VoidChicken("void_chicken",
            "void chicken",
            16, 16, false,
            false,
            false,
            true),
    GoldenChicken("golden_chicken",
            "golden chicken",
            16, 16, false,
            false,
            false,
            true),
    Dinosaur("dinosaur",
            "dinosaur",
            16, 16, false,
            false,
            false,
            true),
    Duck("duck",
            "duck",
            16, 16, false,
            true,
            false,
            true),
    Rabbit("rabbit",
            "rabbit",
            16, 16, false,
            false,
            false,
            true),

    BrownCow("brown_cow",
            "brown cow",
            32, 32, false,
            false,
            true,
            false),
    WhiteCow("white_cow",
            "white cow",
            32, 32, false,
            false,
            true,
            false),
    Sheep("sheep",
            "sheep",
            32, 32, false,
            false,
            true,
            false),
    Goat("goat",
            "goat",
            32, 32, false,
            false,
            true,
            false),
    Pig("pig",
            "pig",
            32, 32, false,
            false,
            true,
            false),

    Ostrich("ostrich",
            "ostrich",
            32, 32, false,
            false,
            true,
            false),

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

    FarmAnimalTypes(String atlasKey, String uiName, float widthInPx, float heightInPx, boolean isBaby, boolean canSwim, boolean liveBarn, boolean liveCoop) {
        this.atlasKey = atlasKey;
        this.uiName = uiName;
        this.widthInPx = widthInPx;
        this.heightInPx = heightInPx;
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

public float getWidthInPx() {
        return widthInPx;
    }

    public float getHeightInPx() {
        return heightInPx;
    }
}
