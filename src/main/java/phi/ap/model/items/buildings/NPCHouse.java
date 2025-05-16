package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.npcStuff.NPCHouseTypes;

public class NPCHouse extends Building {
    NPCHouseTypes type;
    public NPCHouse(NPCHouseTypes type) {
        super(type.getHeight(), type.getWidth());
        setName(type.toString());
        setCoordinate(new Coordinate(type.getSp()));
        fillTile(type.getTile());
        setWalls();
        addItem(type.getText());
    }

    @Override
    public void doTask() {

    }
}
