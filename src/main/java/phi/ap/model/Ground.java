package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

import java.util.ArrayList;

public class Ground {
    private Coordinate coordinate = null; //base on father ground
    private Ground father = null;
    private final int height;
    private final int width;
    private final TileType[][] tiles;
    private final Item[][] items;
    private final ArrayList<Item> holdingItems;

    public Ground(int height, int width) {
        this.height = height;
        this.width = width;
        this.tiles = new TileType[height][width];
        this.items = new Item[height][width];
        this.holdingItems = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TileType getTile(int y, int x) {
        return tiles[y][x];
    }

    public void setTile(int y, int x, TileType type) {
        tiles[y][x] = type;
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
    public void show(int startY, int startX, TileType[][] map) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                map[i + startY][j + startX] = tiles[i][j];
            }
        }
        for (Item holdingItem : holdingItems) {
            holdingItem.show(startY + holdingItem.getCoordinate().getY(),
                    startX + holdingItem.getCoordinate().getX(),
                    map);
        }
    }

    public TileType[][] getTiles() {
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
