package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.items.products.Recipe;

public enum FoodFeatures {
    FriedEgg(new Recipe(), new Eatable(90), 140)
    ;
    private Recipe ingredients;
    private Eatable eatable;
    private int sellPrice;
    private FoodFeatures(Recipe ingredients, Eatable eatable, int sellPrice) {
        this.ingredients = ingredients;
        this.eatable = eatable;
        this.sellPrice = sellPrice;
    }

    abstract void applyBuff();
}
