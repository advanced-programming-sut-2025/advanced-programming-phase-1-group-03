package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum BuildingTypes {
    Coop("C", "", Colors.bg(96), true),
    BigCoop("C", "", Colors.bg(96), true),
    DeluxeCoop("C", "", Colors.bg(96), true),
    Barn("B", "", Colors.bg(56), true),
    BigBarn("B", "", Colors.bg(56), true),
    DeluxeBarn("B", "", Colors.bg(56), true);


    private Tile tile;
    BuildingTypes(String symbol, String fgColor, String bgColor, boolean walkable) {
        tile = new Tile(symbol, fgColor, bgColor, walkable);
    }

    public Tile getTile() {
        return tile;
    }
}
