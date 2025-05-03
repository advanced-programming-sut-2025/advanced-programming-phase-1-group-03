package phi.ap.model.items.buildings;

import phi.ap.model.enums.BuildingTypes;

public class Barn extends Building {
    BuildingTypes buildingType;
    public Barn(int height, int width, BuildingTypes buildingType) {
        super(height, width);
        this.buildingType = buildingType;
    }

    @Override
    public void doTask() {

    }
}
