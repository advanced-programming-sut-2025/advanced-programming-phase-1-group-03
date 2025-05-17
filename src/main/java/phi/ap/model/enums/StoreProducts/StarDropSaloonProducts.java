package phi.ap.model.enums.StoreProducts;

import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.RecipeTypes;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.products.Recipe;

import java.util.ArrayList;

public enum StarDropSaloonProducts implements StoreItemProducer{
    BEER("Beer", "Drink in moderation.", 400, Integer.MAX_VALUE, new Food(1, 1,
            FoodTypes.Beer), StoreTypes.TheStarDropSaloon),
    SALAD("Salad", "A healthy garden salad.", 220, Integer.MAX_VALUE, new Food(1, 1,
            FoodTypes.Salad), StoreTypes.TheStarDropSaloon),
    BREAD("Bread", "A crusty baguette.", 120, Integer.MAX_VALUE, new Food(1, 1,
            FoodTypes.Bread), StoreTypes.TheStarDropSaloon),
    SPAGHETTI("Spaghetti", "An old favorite.", 240, Integer.MAX_VALUE, new Food(1, 1,
            FoodTypes.Spaghetti), StoreTypes.TheStarDropSaloon),
    PIZZA("Pizza", "It's popular for all the right reasons.", 600, Integer.MAX_VALUE,
            new Food(1, 1, FoodTypes.Pizza), StoreTypes.TheStarDropSaloon),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", 300,
            Integer.MAX_VALUE, new Food(1, 1, FoodTypes.Coffee), StoreTypes.TheStarDropSaloon),
    // Recipes
    HASHBROWNSRECIPE("Hashbrowns Recipe", "A recipe to make Hashbrowns", 50, 1,
           FoodTypes.HashBrowns.getRecipe(), StoreTypes.TheStarDropSaloon),
    OMELETRECIPE("Omelet Recipe", "A recipe to make Omelet", 100, 1,
           FoodTypes.Omelet.getRecipe(), StoreTypes.TheStarDropSaloon),
    PANCAKESRECIPE("Pancakes Recipe", "A recipe to make Pancakes", 100, 1,
            FoodTypes.Pancakes.getRecipe(), StoreTypes.TheStarDropSaloon),
    BREADRECIPE("Bread Recipe", "A recipe to make Bread", 100, 1,
            FoodTypes.Bread.getRecipe(), StoreTypes.TheStarDropSaloon),
    TORTILLARECIPE("Tortilla Recipe", "A recipe to make Tortilla", 100, 1,
            FoodTypes.Tortilla.getRecipe(), StoreTypes.TheStarDropSaloon),
    PIZZARECIPE("Pizza Recipe", "A recipe to make Pizza", 150, 1,
            FoodTypes.Pizza.getRecipe(), StoreTypes.TheStarDropSaloon),
    MAKIROLLRECIPE("Maki Roll Recipe", "A recipe to make Maki Roll", 300, 1,
            FoodTypes.MakiRoll.getRecipe(), StoreTypes.TheStarDropSaloon),
    TRIPLESHOTESPRESSORECIPE("Triple Shot Espresso Recipe", "A recipe to make Triple Shot Espresso",
            5000, 1, FoodTypes.TripleShotEspresso.getRecipe(), StoreTypes.TheStarDropSaloon),
    COOKIERECIPE("Cookie Recipe", "A recipe to make Cookie", 300, 1,
            FoodTypes.Cookie.getRecipe(), StoreTypes.TheStarDropSaloon);

    private final String name;
    private final String description;
    private final Integer price;
    private Integer dailyLimit;
    private Integer availableAmount = 1000;

    @Override
    public Item getItem() {
        item.setSellable(true);
        item.setSellPrice(getPrice());
        return item;
    }

    private final Item item;
    private final StoreTypes storeType;

    StarDropSaloonProducts(String name, String description, Integer price, Integer dailyLimit, Item item,
                           StoreTypes storeType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.item = item;
        this.storeType = storeType;
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

    @Override
    public String getNameInStore() {
        return this.name;
    }
}
