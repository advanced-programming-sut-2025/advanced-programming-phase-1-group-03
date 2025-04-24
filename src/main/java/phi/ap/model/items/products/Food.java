package phi.ap.model.items.products;

import phi.ap.model.enums.FoodTypes;

public class Food extends Product {
    FoodTypes foodType;
    public Food(int height, int width, FoodTypes foodType) {
        super(height, width);
        this.foodType = foodType;
    }

    @Override
    public void doTask() {

    }
}
