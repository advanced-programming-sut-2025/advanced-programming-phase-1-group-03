package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.items.products.Recipe;

public enum FoodTypes {
    Hay("Hay",null, null, 0),
    Beer("Beer", null, null, 0),
    Salad("Salad", null, null, 0),
    Bread("Bread", null, null, 0),
    Spaghetti("Spaghetti", null, null, 0),
    Pizza("Pizza", null, null, 0),
    Coffee("Coffee", null, null, 0),
    JojaCola("JojaCola", null, null, 0),
    Sugar("Sugar", null, null, 0),
    WheatFlour("WheatFlour", null, null, 0),
    Rice("Rice", null, null, 0),
    Vinegar("Vinegar", null, null, 0),
    Oil("Oil", null, null, 0),
    FriedEgg("FriedEgg", null, new Eatable(90), 140);
    private Recipe ingredients;
    private String name;
    private Eatable eatable;
    private int sellPrice;
    private FoodTypes(String name, Recipe ingredients, Eatable eatable, int sellPrice) {
        this.ingredients = ingredients;
        this.eatable = eatable;
        this.sellPrice = sellPrice;
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
