package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.Portal;

import java.util.ArrayList;

public class Ground {
    private Coordinate coordinate = null; //base on father ground
    private Ground father = null;
    private final int height;
    private final int width;
    private final Tile[][] tiles;
    private final Item[][] items;
    private final ArrayList<Item> holdingItems;

    public Ground(int height, int width) {
        this.height = height;
        this.width = width;
        this.tiles = new Tile[height][width];
        this.items = new Item[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new Tile(" ", "", "");
            }
        }
        this.holdingItems = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile getTile(int y, int x) {
        if (!isCoordinateValid(y, x)) return  null;
        return tiles[y][x];
    }

    public void setTile(int y, int x, Tile tile) {
        if (!isCoordinateValid(y, x)) return;
        if (tile.getSymbol().equals(" ")) tile.setSymbol(tiles[y][x].getSymbol());
        tiles[y][x].set(new Tile(tile.getSymbol(), tiles[y][x].getFgColor() + tile.getFgColor(),
                tiles[y][x].getBgColor() + tile.getBgColor(), tile.isWalkable()));
    }
    public void fillTile(Tile tile) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setTile(i, j, new Tile(tile));
            }
        }
    }

    public void setWalls() {
        setTile(0, 0, new Tile(TileType.Wall_UL_CORNER));
        setTile(0, width - 1, new Tile(TileType.Wall_UR_CORNER));
        setTile(height - 1, 0, new Tile(TileType.Wall_DL_CORNER));
        setTile(height - 1, width - 1, new Tile(TileType.Wall_DR_CORNER));
        for (int i = 1; i < height - 1; i++) {
            setTile(i, 0, new Tile(TileType.Wall_V_LINE));
            setTile(i, width - 1, new Tile(TileType.Wall_V_LINE));
        }
        for (int j = 1; j < width - 1; j++) {
            setTile(0, j, new Tile(TileType.Wall_H_LINE));
            setTile(height - 1, j, new Tile(TileType.Wall_H_LINE));
        }
    }
    public Item getItem(int y, int x) {
        if (!isCoordinateValid(y, x)) return  null;
        return items[y][x];
    }
    public void addItem(Item item) { //base on ground
        int y = item.getCoordinate().getY();
        int x = item.getCoordinate().getX();
        for (int i = y; i < y + item.getHeight(); i++) {
            for (int j = x; j < x + item.getWidth(); j++) {
                if (items[i][j] == null) continue;
                int sy = items[i][j].getCoordinate().getY();
                int sx = items[i][j].getCoordinate().getX();
                int ey = sy + items[i][j].getHeight() - 1;
                int ex = sy + items[i][j].getWidth() - 1;
                if (sy >= y && sx >= x && ey < y + item.getHeight() && sx < x + item.getWidth()) {
                    removeItem(items[i][j]);
                }
            }
        }

        for (int i = y; i < y + item.getHeight(); i++) {
            for (int j = x; j < x + item.getWidth(); j++) {
                items[i][j] = item;
            }
        }
        holdingItems.add(item);
        item.setFather(this);
    }
    public void removeItem(Item item) { //base on ground;
        int y = item.getCoordinate().getY();
        int x = item.getCoordinate().getX();
        for (int i = y; i < y + item.getHeight(); i++) {
            for (int j = x; j < x + item.getWidth(); j++) {
                items[i][j] = null;
            }
        }
        holdingItems.remove(item);
        item.setFather(null);
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    public void show(int startY, int startX, Tile[][] map) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Tile tile;
                int ii = i + startY;
                int jj = j + startX;
                if (map[ii][jj] != null) {
                    String sym = tiles[i][j].getSymbol();
                    if (sym.equals(" ")) sym = map[ii][jj].getSymbol();
                    tile = new Tile(sym, map[ii][jj].getFgColor() + tiles[i][j].getFgColor(),
                            map[ii][jj].getBgColor() + tiles[i][j].getBgColor());
                } else {
                    tile = new Tile(tiles[i][j]);
                }
                map[i + startY][j + startX] = tile;
            }
        }
        for (Item holdingItem : holdingItems) {
            holdingItem.show(startY + holdingItem.getCoordinate().getY(),
                    startX + holdingItem.getCoordinate().getX(),
                    map);
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Item[][] getItems() {
        return items;
    }

    public ArrayList<Item> getHoldingItems() {
        return holdingItems;
    }

    public void setFather(Ground father) {
        this.father = father;
    }

    public Ground getFather() {
        return father;
    }

    public void setWalkable(boolean walkable) { // it set empty tiles in ground walkable;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (items[i][j] == null) {
                    tiles[i][j].setWalkable(walkable);
                }
            }
        }
    }
    public Coordinate getCoordinateOfCoordinateInChild(Ground child, Coordinate coordinate) {
        return new Coordinate(child.getCoordinate().getY() + coordinate.getY(),
                child.getCoordinate().getX() + coordinate.getX());
    }

    public Coordinate getCoordinateBaseMap() {
        if (this instanceof Map) return new Coordinate(0, 0);
        Coordinate fCoord = father.getCoordinateBaseMap();
        return new Coordinate(fCoord.getY() + coordinate.getY(), fCoord.getX() + coordinate.getX());
    }

    public Coordinate getTileCoordinateBaseMap(int y, int x) {
        Coordinate coord = getCoordinateBaseMap();
        return new Coordinate(coord.getY() + y, coord.getX() + x);
    }

    public Tile getTopTile(int y, int x) {
        if (!isCoordinateValid(y, x)) return null;
        if (items[y][x] == null) return tiles[y][x];
        return items[y][x].getTopTile(y - items[y][x].getCoordinate().getY(),
                x - items[y][x].getCoordinate().getX());
    }

    public Item getTopItem(int y, int x) {
        if (!isCoordinateValid(y, x)) return null;
        if (items[y][x] == null) return null;
        Item res;
        if ((res = items[y][x].getTopItem(y - items[y][x].getCoordinate().getY(), x - items[y][x].getCoordinate().getX())) == null) {
            return items[y][x];
        }
        return res;
    }

    public boolean isCoordinateValid(int y, int x) {
        if (y >= height || y < 0) return false;
        if (x >= width || x < 0) return false;
        return true;
    }

    public int getHeuristicTravelCost(Coordinate source, Coordinate sink) {
        if (!isCoordinateValid(source.getY(), source.getX()) ||
                !isCoordinateValid(sink.getY(), sink.getX())) {
            return Integer.MAX_VALUE;
        }
        return App.getInstance().getMapService().getDistance(source, sink);
    }
    public Location getLocation(Coordinate coordinate) {
        if (!isCoordinateValid(coordinate.getY(), coordinate.getX())) return null;
        Item item;
        if ((item = getItem(coordinate.getY(), coordinate.getX())) == null) return new Location(coordinate, this);
        return item.getLocation(new Coordinate(coordinate.getY() - item.getCoordinate().getY(),
                coordinate.getX() - item.getCoordinate().getX()));
    }

    public ArrayList<Portal> getPortalList() {
        ArrayList<Portal> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (items[i][j] instanceof Portal) list.add((Portal)items[i][j]);
            }
        }
        return list;
    }

    public Portal getPortal(int y, int x) {
        if (!isCoordinateValid(y, x)) return null;
        if (items[y][x] == null) return null;
        if (items[y][x] instanceof Portal) return (Portal) items[y][x];
        return null;
    }
}
