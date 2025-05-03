package phi.ap.model.items.buildings;

import phi.ap.model.enums.BuildingTypes;

public class Coop extends Building {
    BuildingTypes buildingType;
    public Coop(int height, int width, BuildingTypes buildingType) {
        super(height, width);
        this.buildingType = buildingType;
    }

    @Override
    public void doTask() {

    }
}
