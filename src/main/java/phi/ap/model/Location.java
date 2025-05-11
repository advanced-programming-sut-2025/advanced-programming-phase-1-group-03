package phi.ap.model;

import phi.ap.model.enums.FaceWay;
import phi.ap.model.items.Item;
import phi.ap.model.items.PlayerIcon;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.Farm;

import java.util.Objects;

public class Location extends Coordinate{
    private Ground ground;
    private FaceWay faceWay;
    private Player player; //it's optional

    public Location(Coordinate coordinate, Ground ground) {
        super(coordinate);
        this.ground = ground;
        this.faceWay = FaceWay.Down;
    }

    public Location(Coordinate coordinate, Ground ground, FaceWay faceWay) {
        super(coordinate);
        this.ground = ground;
        this.faceWay = faceWay;
    }

    public Location(Location l) {
        super(l.getY(), l.getX());
        this.ground = l.ground;
        this.faceWay = l.faceWay;
        this.player = l.player;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ground, faceWay);
    }

    public Ground getGround() {
        return ground;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isWalkable(int yDiff, int xDiff) {
        int y = getY() + yDiff;
        int x = getX() + xDiff;
        if (!ground.isCoordinateValid(y, x)) return false;
        if (ground.getPortal(y, x) != null) {
            if (player == null) return true;
            Portal p = ground.getPortal(y, x);
            Farm farm = null;
            if (p.getDestination() instanceof Farm) {
                farm = (Farm) p.getDestination();
            }
            if (farm == null) return true;
            return farm.isPlayerAvailable(player);
        }
        return ground.getTopTile(y, x).isWalkable();
    }

    public FaceWay getFaceWayOfWalk(int yDiff, int xDiff) {
        int y = getY() + yDiff;
        int x = getX() + xDiff;
        FaceWay faceWay;
        if (getY() < y) faceWay = FaceWay.Down;
        else if (getY() > y) faceWay = FaceWay.Up;
        else if (getX() < x) faceWay = FaceWay.Right;
        else faceWay = FaceWay.Left;
        return faceWay;
    }

    public boolean walkOne(int yDiff, int xDiff) {
        if (Math.abs(yDiff) != 0 && Math.abs(xDiff) != 0) {
            return false;
        }
        if (yDiff == 0 && xDiff == 0) {
            return false;
        }
        if (!isWalkable(yDiff, xDiff)) return false;
        int y = getY() + yDiff;
        int x = getX() + xDiff;
        faceWay = getFaceWayOfWalk(yDiff, xDiff);
        if (ground.getPortal(y, x) != null) {
            Portal p = ground.getPortal(y, x);
            setY(p.getCoordinateOnDest().getY());
            setX(p.getCoordinateOnDest().getX());
            setGround(p.getDestination());

            return true;
        }
        setY(y);
        setX(x);
        return true;
    }


    public int getCostOfWalkOne(int yDiff, int xDiff) {
        if (!isWalkable(yDiff, xDiff)) return Integer.MAX_VALUE;
        FaceWay newFace;
        int y = getY() + yDiff;
        int x = getX() + xDiff;

        newFace = getFaceWayOfWalk(yDiff, xDiff);
        int res = EnergyManager.walkCost;
        if (newFace != faceWay) res += this.faceWay.getDistance(newFace) * EnergyManager.turningCost;
        return res;
    }

    public FaceWay getFaceWay() {
        return faceWay;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Location loc)) return false;
        if (!super.equals(loc))return false;
        return (ground.equals(loc.ground) && faceWay.equals(loc.faceWay));
    }

    public Coordinate getCoordinate() {
        return new Coordinate(super.getY(), super.getX());
    }

    public Tile getTileDiff(int yDiff, int xDiff) {
        return ground.getTile(getY() + yDiff, getX() + xDiff);
    }

    public Tile getTopTileDiff(int yDiff, int xDiff) {
        return ground.getTopTile(getY() + yDiff, getX() + xDiff);
    }

    public Item getTopItemDiff(int yDiff, int xDiff) {
        return ground.getTopItem(getY() + yDiff, getX() + xDiff);
    }

    public Item getItemDiff(int yDiff, int xDiff) {
        return ground.getItem(getY() + yDiff, getX() + xDiff);
    }

    public Portal getPortalDiff(int yDiff, int xDiff) {
        return ground.getPortal(getY() + yDiff, getX() + xDiff);
    }

    public void setFaceWay(FaceWay faceWay) {
        this.faceWay = faceWay;
    }

}
