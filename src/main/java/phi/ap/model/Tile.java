package phi.ap.model;

import phi.ap.model.enums.Colors;
import phi.ap.model.enums.TileType;

public class Tile {
    private String bgColor, fgColor;
    private String symbol;

    public Tile(String symbol, String fgColor, String bgColor) {
        this.symbol = symbol;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
    }

    public Tile(TileType tileType) {
        this.symbol = tileType.getTile().getSymbol();
        this.fgColor = tileType.getTile().getFgColor();
        this.bgColor = tileType.getTile().getBgColor();
    }

    public Tile(Tile tile) {
        this.symbol = tile.getSymbol();
        this.fgColor = tile.getFgColor();
        this.bgColor = tile.getBgColor();
    }

    public String getBgColor() {
        return bgColor;
    }

    public String getFgColor() {
        return fgColor;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setFgColor(String fgColor) {
        this.fgColor = fgColor;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return bgColor + fgColor + symbol;
    }

    public String toString(boolean needReset) {
        if (needReset) {
            return toString() + Colors.RESET;
        } else {
            return toString();
        }
    }
}
