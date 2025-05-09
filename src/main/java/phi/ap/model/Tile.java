package phi.ap.model;

import phi.ap.model.enums.Colors;
import phi.ap.model.enums.TileType;

public class Tile {
    private String bgColor, fgColor;
    private String symbol;
    private boolean walkable = false;

    public Tile(String symbol, String fgColor, String bgColor) {
        this.symbol = symbol;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.walkable = false;
    }

    public Tile(String symbol,String fgColor, String bgColor, boolean walkable) {
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.symbol = symbol;
        this.walkable = walkable;
    }

    public Tile(TileType tileType) {
        this.symbol = tileType.getTile().getSymbol();
        this.fgColor = tileType.getTile().getFgColor();
        this.bgColor = tileType.getTile().getBgColor();
        this.walkable = tileType.getTile().isWalkable();
    }


    public Tile(Tile tile) {
        this.symbol = tile.getSymbol();
        this.fgColor = tile.getFgColor();
        this.bgColor = tile.getBgColor();
        this.walkable = tile.isWalkable();
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

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
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

    public Tile getTile() {
        return this;
    }


    public void set (Tile tile) {
        this.bgColor = tile.getBgColor();
        this.fgColor = tile.getFgColor();
        this.symbol = tile.getSymbol();
        this.walkable = tile.isWalkable();
    }
}
