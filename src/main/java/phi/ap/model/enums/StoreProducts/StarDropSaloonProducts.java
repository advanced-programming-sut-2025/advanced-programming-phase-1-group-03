package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.RecipeTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.products.Recipe;

public enum StarDropSaloonProducts {
    BEER("Beer", "Drink in moderation.", 400, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Beer), StoreTypes.TheStarDropSaloon),
    SALAD("Salad", "A healthy garden salad.", 220, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Salad), StoreTypes.TheStarDropSaloon),
    BREAD("Bread", "A crusty baguette.", 120, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Bread), StoreTypes.TheStarDropSaloon),
    SPAGHETTI("Spaghetti", "An old favorite.", 240, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Spaghetti), StoreTypes.TheStarDropSaloon),
    PIZZA("Pizza", "It's popular for all the right reasons.", 600, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Pizza), StoreTypes.TheStarDropSaloon),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", 300, Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Coffee), StoreTypes.TheStarDropSaloon),
    // Recipes
    HASHBROWNSRECIPE("Hashbrowns Recipe", "A recipe to make Hashbrowns", 50, 1, new Recipe(1, 1, null, null, RecipeTypes.Hashbrowns), StoreTypes.TheStarDropSaloon),
    OMELETRECIPE("Omelet Recipe", "A recipe to make Omelet", 100, 1, new Recipe(1, 1, null, null, RecipeTypes.Omelet), StoreTypes.TheStarDropSaloon),
    PANCAKESRECIPE("Pancakes Recipe", "A recipe to make Pancakes", 100, 1, new Recipe(1, 1, null, null, RecipeTypes.Pancakes), StoreTypes.TheStarDropSaloon),
    BREADRECIPE("Bread Recipe", "A recipe to make Bread", 100, 1, new Recipe(1, 1, null, null, RecipeTypes.Bread), StoreTypes.TheStarDropSaloon),
    TORTILLARECIPE("Tortilla Recipe", "A recipe to make Tortilla", 100, 1, new Recipe(1, 1, null, null, RecipeTypes.Tortilla), StoreTypes.TheStarDropSaloon),
    PIZZARECIPE("Pizza Recipe", "A recipe to make Pizza", 150, 1, new Recipe(1, 1, null, null, RecipeTypes.Pizza), StoreTypes.TheStarDropSaloon),
    MAKIROLLRECIPE("Maki Roll Recipe", "A recipe to make Maki Roll", 300, 1, new Recipe(1, 1, null, null, RecipeTypes.MakiRoll), StoreTypes.TheStarDropSaloon),
    TRIPLESHOTESPRESSORECIPE("Triple Shot Espresso Recipe", "A recipe to make Triple Shot Espresso", 5000, 1, new Recipe(1, 1, null, null, RecipeTypes.TripleShotEspresso), StoreTypes.TheStarDropSaloon),
    COOKIERECIPE("Cookie Recipe", "A recipe to make Cookie", 300, 1, new Recipe(1, 1, null, null, RecipeTypes.Cookie), StoreTypes.TheStarDropSaloon);

    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;
    private final Item item;
    private final StoreTypes storeType;

    StarDropSaloonProducts(String name, String description, Integer price, Integer dailyLimit, Item item, StoreTypes storeType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.storeType = storeType;
    }

    public static Result<String> purchase(String productName, String amountString) {
        int amount = Integer.parseInt(amountString);
        StarDropSaloonProducts starDropSaloonProducts;
        try {
            starDropSaloonProducts = StarDropSaloonProducts.valueOf(productName);
        }
        catch (IllegalArgumentException e) {
            return new Result<>(false, "There is no product with this name.");
        }
        if(amount > starDropSaloonProducts.availableAmount) {
            return new Result<>(false, "There is not enough amount of this product.");
        }
        else if(amount > starDropSaloonProducts.dailyLimit) {
            return new Result<>(false, "You can't purchase this amount of product on this day.");
        }
        else if(amount * starDropSaloonProducts.price > Game.getInstance().getCurrentPlayer().getGold()) {
            return new Result<>(false, "You don't have enough money.");
        }
        starDropSaloonProducts.availableAmount -= amount;
        starDropSaloonProducts.dailyLimit -= amount;
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() - amount * starDropSaloonProducts.price);
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(starDropSaloonProducts.item, amount);
        return new Result<>(true, "Item purchased successfully");
    }

    public static Result<String> showAllProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(StarDropSaloonProducts starDropSaloonProducts : StarDropSaloonProducts.values()) {
            stringBuilder.append("Name : " + "\"" + starDropSaloonProducts.getName() + "\"" + "     " + "Price: "  + starDropSaloonProducts.getPrice() + "g" + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }

    public static Result<String> showAvailableProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        for(StarDropSaloonProducts starDropSaloonProducts : StarDropSaloonProducts.values()) {
            if(starDropSaloonProducts.availableAmount > 0 && starDropSaloonProducts.dailyLimit > 0)
                stringBuilder.append("Name : " + "\"" + starDropSaloonProducts.getName() + "\"" + "     " + "Price: "  + starDropSaloonProducts.getPrice() + "g" + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
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
