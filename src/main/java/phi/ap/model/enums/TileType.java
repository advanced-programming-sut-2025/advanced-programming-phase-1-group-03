package phi.ap.model.enums;

import phi.ap.model.Tile;

public enum TileType {
    Pillar("●", Colors.fg(195), ""),
    GreenhouseMargin(" ", "", Colors.bg(94), true),
    Farm(".", "" ,Colors.bg(77), true),
    Dirt(".", Colors.fg(230) ,Colors.bg(172), true),
    PlowedDirt(".", Colors.fg(172) ,Colors.bg(3), true),
    NPCVillage(".", "" ,Colors.bg(65), true),
    Lake("#", "", Colors.bg(11)),
    Cottage("^", "", Colors.bg(22), true),
    Greenhouse(".", "", Colors.bg(71)),
    Quarry(".", "", Colors.bg(240), true),
    Ground(" ", Colors.fg(231), Colors.bg(0)),
    Wall("#", Colors.fg(0), ""),
    Wall_UL_CORNER("┌", Colors.fg(0), ""),
    Wall_UR_CORNER("┐", Colors.fg(0), ""),
    Wall_DL_CORNER("└", Colors.fg(0), ""),
    Wall_DR_CORNER("┘", Colors.fg(0), ""),
    Wall_H_LINE("─", Colors.fg(0), ""),
    Wall_V_LINE("│", Colors.fg(0), ""),
    Door("+", Colors.fg(0), "", true),
    Player("⍢", Colors.fg(57), ""),
    Wood("=", Colors.fg(94), ""),
    Water("~", "", Colors.bg(19), false),
    BlackSmithStore(" ", "" ,Colors.bg(77), true),
    MarnieSmithStore(" ", "" ,Colors.bg(133), true),
    StarDropSaloon(" ", "" ,Colors.bg(69), true),
    CarpenterShop(" ", "" ,Colors.bg(85), true),
    JojaMart(" ", "" ,Colors.bg(155), true),
    PierreGeneralStore(" ", "" ,Colors.bg(254), true),
    FishShop(" ", "" ,Colors.bg(52), true),
    ShippingBin("S", "" ,Colors.bg(120), false),
    GiantBackGround(" ", "" ,Colors.bg(124), false),
    A("A", "", Colors.bg(65), true),
    B("B", "", Colors.bg(65), true),
    C("C", "", Colors.bg(65), true),
    D("D", "", Colors.bg(65), true),
    E("E", "", Colors.bg(65), true),
    F("F", "", Colors.bg(65), true),
    G("G", "", Colors.bg(65), true),
    H("H", "", Colors.bg(65), true),
    I("I", "", Colors.bg(65), true),
    J("J", "", Colors.bg(65), true),
    K("K", "", Colors.bg(65), true),
    L("L", "", Colors.bg(65), true),
    M("M", "", Colors.bg(65), true),
    N("N", "", Colors.bg(65), true),
    O("O", "", Colors.bg(65), true),
    P("P", "", Colors.bg(65), true),
    Q("Q", "", Colors.bg(65), true),
    R("R", "", Colors.bg(65), true),
    S("S", "", Colors.bg(65), true),
    T("T", "", Colors.bg(65), true),
    U("U", "", Colors.bg(65), true),
    V("V", "", Colors.bg(65), true),
    W("W", "", Colors.bg(65), true),
    X("X", "", Colors.bg(65), true),
    Y("Y", "", Colors.bg(65), true),
    Z("Z", "", Colors.bg(65), true),
    a("a", "", Colors.bg(65), true),
    b("b", "", Colors.bg(65), true),
    c("c", "", Colors.bg(65), true),
    d("d", "", Colors.bg(65), true),
    e("e", "", Colors.bg(65), true),
    f("f", "", Colors.bg(65), true),
    g("g", "", Colors.bg(65), true),
    h("h", "", Colors.bg(65), true),
    i("i", "", Colors.bg(65), true),
    j("j", "", Colors.bg(65), true),
    k("k", "", Colors.bg(65), true),
    l("l", "", Colors.bg(65), true),
    m("m", "", Colors.bg(65), true),
    n("n", "", Colors.bg(65), true),
    o("o", "", Colors.bg(65), true),
    p("p", "", Colors.bg(65), true),
    q("q", "", Colors.bg(65), true),
    r("r", "", Colors.bg(65), true),
    s("s", "", Colors.bg(65), true),
    t("t", "", Colors.bg(65), true),
    u("u", "", Colors.bg(65), true),
    v("v", "", Colors.bg(65), true),
    w("w", "", Colors.bg(65), true),
    x("x", "", Colors.bg(65), true),
    y("y", "", Colors.bg(65), true),
    z("z", "", Colors.bg(65), true),
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
