package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum BuildingTypes {
    Coop("Coop", "C", "", Colors.bg(96), true, 4),
    BigCoop("BigCoop", "C", "", Colors.bg(96), true, 8),
    DeluxeCoop("DeluxeCoop", "C", "", Colors.bg(96), true, 12),
    Barn("Barn", "B", "", Colors.bg(56), true, 4),
    BigBarn("BigBarn", "B", "", Colors.bg(56), true, 8),
    DeluxeBarn("DeluxeBarn", "B", "", Colors.bg(56), true, 12)
    ;


    private final String name;
    private Tile tile;
    private final int capacity;

    BuildingTypes(String name, String symbol, String fgColor, String bgColor, boolean walkable, int capacity) {
        tile = new Tile(symbol, fgColor, bgColor, walkable);
        this.capacity = capacity;
        this.name = name;
    }

    public Tile getTile() {
        return tile;
    }
    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }
    public static BuildingTypes getType(String name) {
        BuildingTypes buildingType;
        try {
            buildingType = BuildingTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return buildingType;
    }
}
