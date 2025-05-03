package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Ground;
import phi.ap.model.Tile;
import phi.ap.model.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class NPCVillage extends Building {
    public NPCVillage() {
        super(40, 40);
        fillTile(TileType.NPCVillage.getTile());
        setWalls();
        setDoors(new ArrayList<>(List.of(
                new Tile(TileType.Door.getTile(), new Coordinate(0, getWidth() / 2)),
                new Tile(TileType.Door.getTile(), new Coordinate(getHeight() / 2, 0)),
                new Tile(TileType.Door.getTile(), new Coordinate(getHeight() / 2, getWidth() - 1)),
                new Tile(TileType.Door.getTile(), new Coordinate(getHeight() - 1, getWidth() / 2)))));
    }

    @Override
    public void doTask() {

    }
}
