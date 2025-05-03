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

    }

    @Override
    public void doTask() {

    }
}
