package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.BuildingTypes;
import phi.ap.model.items.Animal;

import java.util.ArrayList;

public class AnimalHouse extends Building {
    BuildingTypes buildingType;
    private ArrayList<Animal> kippingAnimals = new ArrayList<>();

    public AnimalHouse(int height, int width, BuildingTypes buildingType, Coordinate coordinate) {
        super(height, width);
        setName(buildingType.getName());
        this.buildingType = buildingType;
        setCoordinate(coordinate);
        fillTile(buildingType.getTile());
    }

    public BuildingTypes getBuildingType() {
        return buildingType;
    }

    public boolean addAnimal(Animal animal ) {
        if(kippingAnimals.size() >= buildingType.getCapacity())
            return false;
        kippingAnimals.add(animal);
        return true;
    }

    public void setBuildingType(BuildingTypes buildingType) {
        this.buildingType = buildingType;
    }
    @Override
    public void doTask() {

    }
}
