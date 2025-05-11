package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum TileType {
    Farm(".", "" ,Colors.bg(77), true),
    Dirt(".", "" ,Colors.bg(172), true),
    PlowedDirt(".", Colors.fg(3) ,Colors.bg(172), true),
    NPCVillage(".", "" ,Colors.bg(65), true),
    Lake("#", "", Colors.bg(11)), //TODO we don't need Lake TileType
    Cottage("^", "", Colors.bg(22), true),
    Greenhouse("G", "", Colors.bg(71)),
    Quarry("Q", "", Colors.bg(240), true),
    Ground(".", Colors.fg(231), Colors.bg(0)),
    Wall("#", Colors.fg(0), ""),
    Wall_UL_CORNER("┌", Colors.fg(0), ""),
    Wall_UR_CORNER("┐", Colors.fg(0), ""),
    Wall_DL_CORNER("└", Colors.fg(0), ""),
    Wall_DR_CORNER("┘", Colors.fg(0), ""),
    Wall_H_LINE("─", Colors.fg(0), ""),
    Wall_V_LINE("│", Colors.fg(0), ""),
    Door("+", Colors.fg(0), "", true),
    Player("⍢", Colors.fg(57), ""),
    Wood("#", Colors.fg(94), ""),
    Water("~", "", Colors.bg(19)),
    ;
    private final Tile tile;

    TileType(String symbol, String fgColor, String bgColor) {

        tile = new Tile(symbol, fgColor, bgColor, false);
    }
    TileType(String symbol, String fgColor, String bgColor, boolean walkable) {
        tile = new Tile(symbol, fgColor, bgColor, walkable);
    }

    public Tile getTile() {
        return tile;
    }

    public Tile getTileWithFaceWay(FaceWay faceWay) {
        Tile res = new Tile(tile);
        res.setSymbol(faceWay.getSymbol());
        return res;
    }
}
