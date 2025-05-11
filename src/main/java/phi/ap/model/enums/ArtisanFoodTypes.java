package phi.ap.model.enums;

import phi.ap.model.items.products.Recipe;

public enum ArtisanFoodTypes {
    ;
    // HourBase
    private  String name;
    private int processingTime;
    private Recipe recipe;
    private int sellPrice;
    private int energy;

    public int getEnergy() {
        return energy;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return getName();
    }
}
