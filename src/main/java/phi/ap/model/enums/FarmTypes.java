package phi.ap.model.enums;

import phi.ap.model.Coordinate;
import phi.ap.model.items.buildings.Building;
import phi.ap.model.items.buildings.BuildingStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FarmTypes {
    STANDARD(
            new BuildingStructure(new Coordinate(1, 1), 4, 4),
            new BuildingStructure(new Coordinate(17, 7), 6, 5),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(8, 3), 7, 7)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(25, 20), 4, 5)
            ))
    ),
    RIVERLAND(
            new BuildingStructure(new Coordinate(3, 3), 4, 4),
            new BuildingStructure(new Coordinate(4, 24), 6, 5),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(15, 5), 10, 10)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(20, 22), 4, 6),
                    new BuildingStructure(new Coordinate(18, 18), 3, 4)
            ))
    ),
    FOREST(
            new BuildingStructure(new Coordinate(25, 2), 4, 4),
            new BuildingStructure(new Coordinate(2, 25), 6, 5),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(20, 8), 5, 7),
                    new BuildingStructure(new Coordinate(23, 12), 3, 3)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(22, 18), 6, 6)
            ))
    ),
    HILLTOP(
            new BuildingStructure(new Coordinate(2, 4), 4, 4),
            new BuildingStructure(new Coordinate(5, 24), 6, 5),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(18, 10), 7, 5)
            )),
            new ArrayList<>(List.of(
                    new BuildingStructure(new Coordinate(26, 6), 5, 6),
                    new BuildingStructure(new Coordinate(28, 10), 2, 4)
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
