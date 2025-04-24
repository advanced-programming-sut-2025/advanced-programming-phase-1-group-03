package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;

public class BuildingStructure {
    private final Coordinate coordinate;
    private final int height;
    private final int width;

    public BuildingStructure(Coordinate coordinate, int height, int width) {
        this.coordinate = coordinate;
        this.height = height;
        this.width = width;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
