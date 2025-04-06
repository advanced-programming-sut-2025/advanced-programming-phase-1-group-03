package phi.ap.model;

import phi.ap.model.enums.TileType;

public class Ground {
    private final int height;
    private final int width;
    TileType[][] tiles;

    public Ground(int height, int width) {
        this.height = height;
        this.width = width;
        this.tiles = new TileType[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TileType getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTile(int x, int y, TileType type) {

    }
}
