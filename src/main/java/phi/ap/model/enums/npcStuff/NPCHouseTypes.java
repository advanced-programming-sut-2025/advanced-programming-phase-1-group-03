package phi.ap.model.enums.npcStuff;

import phi.ap.model.Coordinate;
import phi.ap.model.Text;
import phi.ap.model.Tile;
import phi.ap.model.enums.Colors;

public enum NPCHouseTypes {
    SebastianHouse(5, 11, new Coordinate(13, 4), new Text("Sebastian", new Coordinate(2, 1)), new Tile(" ", "", Colors.bg(53), true), new Coordinate(3, 2)),
    AbigailHouse(5, 11, new Coordinate(11, 20), new Text("Abigail", new Coordinate(2, 2)), new Tile(" ", "", Colors.bg(162), true), new Coordinate(1, 7)),
    HarveyHouse(5, 8, new Coordinate(17, 30), new Text("Harvey", new Coordinate(2, 1)), new Tile(" ", "", Colors.bg(12), true), new Coordinate(1, 3)),
    LiaHouse(3, 7, new Coordinate(20, 4), new Text("Lia", new Coordinate(1, 2)), new Tile(" ", "", Colors.bg(227), true), new Coordinate(1, 1)),
    RobinJrHouse(3, 13, new Coordinate(19, 15), new Text("RobinJr", new Coordinate(1, 3)), new Tile(" ", "", Colors.bg(124), true), new Coordinate(1, 11))
    ;
    private int height;
    private int width;
    private Coordinate sp;
    private Text text;
    private Tile tile;
    private Coordinate npcCoordinate;

    NPCHouseTypes(int height, int width, Coordinate sp, Text text, Tile tile, Coordinate npcCoordinate) {
        this.height = height;
        this.width = width;
        this.sp = sp;
        this.text = text;
        this.tile = tile;
        this.npcCoordinate = npcCoordinate;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Coordinate getSp() {
        return sp;
    }

    public Text getText() {
        return text;
    }

    public Tile getTile() {
        return tile;
    }
    public Coordinate getNpcCoordinate() {
        return npcCoordinate;
    }
}
