package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

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
        this.holdingItems = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile getTile(int y, int x) {
        return tiles[y][x];
    }

    public void setTile(int y, int x, Tile tile) {
        tiles[y][x] = tile;
    }
    public void fillTile(Tile tile) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = tile;
            }
        }
    }
    public Item getItem(int y, int x) {
        return items[y][x];
    }
    public void setItem(int y, int x, Item item) {
        items[y][x] = item;
    }
    public void addItem(Item item) { //base on ground
        int y = item.getCoordinate().getY();
        int x = item.getCoordinate().getX();
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
                    tile = new Tile(tiles[i][j].getSymbol(), map[ii][jj].getFgColor() + tiles[i][j].getFgColor(),
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
}
