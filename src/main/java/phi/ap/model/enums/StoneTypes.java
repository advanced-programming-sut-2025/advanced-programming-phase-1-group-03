package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum StoneTypes {
    RegularStone("@", Colors.fg(234), "")
    ;

    private Tile tile;

    StoneTypes(String symbol, String fgColor, String bgColor) {
        this.tile = new Tile(symbol, fgColor, bgColor);
    }

    public Tile getTile() {
        return tile;
    }
}
