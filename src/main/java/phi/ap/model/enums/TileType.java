package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum TileType {
    Farm(".", "" ,Colors.bg(77)),
    Lake("~", "", Colors.bg(19)),
    Cottage("^", "", Colors.bg(22)),
    Greenhouse("G", "", Colors.bg(71)),
    Quarry("Q", "", Colors.bg(240)),
    Ground(".", "", Colors.BLACK_BACKGROUND.toString())
    ;
    private final Tile tile;

    TileType(String symbol, String fgColor, String bgColor) {
        tile = new Tile(symbol, fgColor, bgColor);
    }

    public Tile getTile() {
        return tile;
    }
}
