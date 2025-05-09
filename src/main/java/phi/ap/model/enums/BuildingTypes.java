package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum BuildingTypes {
    Coop("C", "", Colors.bg(96), true, 4),
    BigCoop("C", "", Colors.bg(96), true, 8),
    DeluxeCoop("C", "", Colors.bg(96), true, 12),
    Barn("B", "", Colors.bg(56), true, 4),
    BigBarn("B", "", Colors.bg(56), true, 8),
    DeluxeBarn("B", "", Colors.bg(56), true, 12)
    ;

    private Tile tile;
    private final int capacity;
    BuildingTypes(String symbol, String fgColor, String bgColor, boolean walkable, int capacity) {
        tile = new Tile(symbol, fgColor, bgColor, walkable);
        this.capacity = capacity;
    }

    public Tile getTile() {
        return tile;
    }
    public int getCapacity() {
        return capacity;
    }
}
