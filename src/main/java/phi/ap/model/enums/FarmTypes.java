package phi.ap.model.enums;

import phi.ap.model.Coordinate;
import phi.ap.model.items.buildings.Building;
import phi.ap.model.items.buildings.BuildingStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FarmTypes {
    STANDARD(
            new BuildingStructure(new Coordinate(1, 1), 4 + 1, 4 + 1),
            new BuildingStructure(new Coordinate(1, 12), 5 + 1, 6 + 1),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(22, 10), 7, 12)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(1, 22), 6, 7)
            ))
    ),
    RIVERLAND(
            new BuildingStructure(new Coordinate(1, 1), 4 + 1, 4 + 1),
            new BuildingStructure(new Coordinate(3, 15), 5 + 1, 6 + 1),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(24, 7), 5, 17),
                    new BuildingStructure(new Coordinate(1, 25), 7, 4)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(10, 3), 4, 5)
            ))
    ),
    FOREST(
            new BuildingStructure(new Coordinate(13, 13), 4 + 1, 4 + 1),
            new BuildingStructure(new Coordinate(1, 1), 5 + 1, 6 + 1),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(24, 7), 4, 14)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(2, 21), 5, 8)
            ))
    ),
    HILLTOP(
            new BuildingStructure(new Coordinate(25, 24), 4 + 1, 4 + 1),
            new BuildingStructure(new Coordinate(1, 22), 5 + 1, 6 + 1),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(25, 3), 3, 12)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(2, 2), 4, 7),
                    new BuildingStructure(new Coordinate(13, 24), 4, 5)
            ))
    );
    ;
    private final BuildingStructure cottage;
    private final BuildingStructure greenhouse;
    private final ArrayList<BuildingStructure> lakes;
    private final ArrayList<BuildingStructure> quarries;

    FarmTypes(BuildingStructure cottage, BuildingStructure greenhouse,
              ArrayList<BuildingStructure> lakes, ArrayList<BuildingStructure> quarries) {
        this.cottage = cottage;
        this.greenhouse = greenhouse;
        this.lakes = lakes;
        this.quarries = quarries;
    }

    public BuildingStructure getCottage() {
        return cottage;
    }

    public BuildingStructure getGreenhouse() {
        return greenhouse;
    }

    public ArrayList<BuildingStructure> getLakes() {
        return lakes;
    }

    public ArrayList<BuildingStructure> getQuarries() {
        return quarries;
    }
}
