package phi.ap.model.items.products;

import phi.ap.model.Eatable;
import phi.ap.model.enums.FoodTypes;

public class Food extends Product {
    String foodName;
    FoodTypes foodType = null;
    public Food(int height, int width, String foodName, int sellPrice, Eatable eatable) {
        super(height, width);
        this.foodName = foodName;
        setName(foodName);
        setMaxStackSize(5);
        setSellable(true);
        setSellPrice(sellPrice);
        setEatable(eatable);
    }
    public Food(int height, int width, FoodTypes foodType) {
        super(height, width);
        this.foodType = foodType;
        setName(foodType.getName());
        setMaxStackSize(5);
        setSellable(true);
        setSellPrice(foodType.getSellPrice());
        setEatable(foodType.getEatable());
        setWaitingTime(foodType.getProcessingTime());
    }

    public FoodTypes getFoodType() {
        if(foodType == null)
            foodType = FoodTypes.valueOf(foodName);
        return foodType;
    }


    @Override
    public void doTask() {

    }
}
