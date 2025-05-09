package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.BuildingTypes;

public class AnimalHouse extends Building {
    BuildingTypes buildingType;

    public AnimalHouse(int height, int width, BuildingTypes buildingType, Coordinate coordinate) {
        super(height, width);
        this.buildingType = buildingType;
        setCoordinate(coordinate);
        fillTile(buildingType.getTile());
    }

    public BuildingTypes getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingTypes buildingType) {
        this.buildingType = buildingType;
    }
    @Override
    public void doTask() {

    }
}
