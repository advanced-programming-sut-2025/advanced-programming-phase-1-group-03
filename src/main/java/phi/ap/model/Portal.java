package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

public class Portal extends Item {
    private Ground destination;
    private Coordinate coordinateOnDest;

    public Portal(int height, int width, Ground destination, Coordinate coordinateOnDest) {
        super(height, width);
        this.destination = destination;
        this.coordinateOnDest = coordinateOnDest;
    }

    public Ground getDestination() {
        return destination;
    }

    public Coordinate getCoordinateOnDest() {
        return coordinateOnDest;
    }

    public void setDestination(Ground destination) {
        this.destination = destination;
    }

    public void setCoordinateOnDest(Coordinate coordinateOnDest) {
        this.coordinateOnDest = coordinateOnDest;
    }

    public static void makePortalOneWay(Ground origin, Coordinate originCoord, Ground destination, Coordinate destCoord, Tile shape) {
        Portal portal = new Portal(1, 1, destination, destCoord);
        portal.setCoordinate(originCoord);
        if (shape != null) {
            portal.setTile(0, 0, shape);
        }
        origin.addItem(portal);
    }
    public static void makePortalTwoWay(Ground origin, Coordinate originCoord, Ground destination, Coordinate destCoord, Tile shape) {
        Portal portal1 = new Portal(1, 1, destination, destCoord);
        portal1.setCoordinate(originCoord);
        Portal portal2 = new Portal(1, 1, origin, originCoord);
        portal2.setCoordinate(destCoord);
        if (shape != null) {
            portal1.setTile(0, 0, shape);
            portal2.setTile(0, 0, shape);
        }
        origin.addItem(portal1);
        destination.addItem(portal2);
    }

    public static void makeMiddleDoor(Ground child, Ground father, Tile shape) {
        int h = child.getHeight() - 1;
        int w = child.getWidth() / 2;
        makePortalTwoWay(child, new Coordinate(h, w), father,
                new Coordinate(child.getCoordinate().getY() + h, child.getCoordinate().getX() + w), shape);
    }

    @Override
    public void doTask() {

    }
}
