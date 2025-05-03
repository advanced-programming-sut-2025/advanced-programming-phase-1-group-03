package phi.ap.model;

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

    public static void makePortalOneWay(Ground origin, Coordinate originCoord, Ground destination, Coordinate destCoord) {
        Portal portal = new Portal(1, 1, destination, destCoord);
        origin.setPortal(originCoord.getY(), originCoord.getX(), portal);
    }
    public static void makePortalTwoWay(Ground origin, Coordinate originCoord, Ground destination, Coordinate destCoord) {
        Portal portal1 = new Portal(1, 1, destination, destCoord);
        Portal portal2 = new Portal(1, 1, origin, originCoord);
        origin.setPortal(originCoord.getY(), originCoord.getX(), portal1);
        destination.setPortal(destCoord.getY(), destCoord.getX(), portal2);
    }

    @Override
    public void doTask() {

    }
}
