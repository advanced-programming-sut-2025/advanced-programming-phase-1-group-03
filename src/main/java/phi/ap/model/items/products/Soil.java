package phi.ap.model.items.products;

import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.SoilTypes;

public class Soil extends Product{
    SoilTypes soilType;
    public Soil(int height, int width, SoilTypes soilType) {
        super(height, width);
        this.soilType = soilType;
    }

    @Override
    public void doTask() {

    }
}
