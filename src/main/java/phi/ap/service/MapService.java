package phi.ap.service;

import phi.ap.model.*;

public class MapService {
    private Map map;

    public MapService(Map map) {
        this.map = map;
    }

    public void addDoor(Ground father, Ground child, Tile doorShape) {
        child.setTile(doorShape.getCoordinate().getY(), doorShape.getCoordinate().getX(),
                doorShape.getTile());
        Portal.makePortalTwoWay(father, father.getCoordinateOfCoordinateInChild(child, doorShape.getCoordinate()),
                child, doorShape.getCoordinate());
    }
}
