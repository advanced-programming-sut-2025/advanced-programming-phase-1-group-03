package phi.ap.model.enums;

public enum SellingProducts {
    CopperOre("Copper Ore", "A common ore that can be smelted into bars.", 75, Integer.MAX_VALUE),
    IronOre("Iron Ore", "A fairly common ore that can be smelted into bars.", 150, Integer.MAX_VALUE),
    Coal("Coal", "A combustible rock that is useful for crafting and smelting.", 150, Integer.MAX_VALUE),
    GoldOre("Cold Ore", "A precious ore that can be smelted into bars.", 400, Integer.MAX_VALUE);


    private final String name;
    private final String description;
    private final Integer price;
    private final Integer dailyLimit;

    SellingProducts(String name, String description, Integer price, Integer dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }
}
