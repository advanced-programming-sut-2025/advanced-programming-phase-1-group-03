package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum StoneTypes {
    RegularStone("@", Colors.fg(234), "", "Regular Stone")
    ;

    private Tile tile;
    private String name;
    StoneTypes(String symbol, String fgColor, String bgColor, String name) {
        this.tile = new Tile(symbol, fgColor, bgColor);
        this.name = name;
    }

    public Tile getTile() {
        return tile;
    }

    public StoneTypes getType(String name) {
        StoneTypes stoneType;
        try {
            stoneType = StoneTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return stoneType;
    }
}
