package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.items.products.Recipe;

public enum FoodTypes {
    Hay(null, null, 0),
    Beer(null, null, 0),
    Salad(null, null, 0),
    Bread(null, null, 0),
    Spaghetti(null, null, 0),
    Pizza(null, null, 0),
    Coffee(null, null, 0),
    JojaCola(null, null, 0),
    Sugar(null, null, 0),
    WheatFlour(null, null, 0),
    Rice(null, null, 0),
    Vinegar(null, null, 0),
    Oil(null, null, 0),
    FriedEgg(null, new Eatable(90), 140);
    private Recipe ingredients;
    private Eatable eatable;
    private int sellPrice;
    private FoodTypes(Recipe ingredients, Eatable eatable, int sellPrice) {
        this.ingredients = ingredients;
        this.eatable = eatable;
        this.sellPrice = sellPrice;
    }
}
