package phi.ap.model;

import phi.ap.model.enums.FaceWay;

public class Location extends Coordinate{
    private Ground ground;
    FaceWay faceWay;

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

    public Location(int y, int x, Ground ground) {
        super(y, x);
        this.ground = ground;
        this.faceWay = FaceWay.Down;
    }

    public Location(Location l) {
        super(l.getY(), l.getX());
        this.ground = l.ground;
        this.faceWay = l.faceWay;
    }

    public Ground getGround() {
        return ground;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public boolean isWalkable(int yDiff, int xDiff) {
        if (Math.abs(yDiff) > 0 && Math.abs(xDiff) > 0) {
            return false;
        }
        if (yDiff == 0 && xDiff == 0) {
            return false;
        }
        int y = getY() + yDiff;
        int x = getX() + xDiff;
        if (!ground.isCoordinateValid(y, x)) return false;
        if (ground.getPortal(y, x) != null) return true;
        if (ground.getItem(y, x) == null) {
            return ground.getTile(y, x).isWalkable();
        } else {
            //TODO : decide if can go on the roof?:), in case there is no door but it's no wall neither.
            // it can walk on the object. put this?
            return false;
        }
    }

    public boolean walkOne(int yDiff, int xDiff) {
        if (!isWalkable(yDiff, xDiff)) return false;
        int y = getY() + yDiff;
        int x = getX() + xDiff;
        if (ground.getPortal(y, x) != null) {
            Portal p = ground.getPortal(y, x);
             setY(p.getCoordinate().getY());
             setX(p.getCoordinate().getX());
             setGround(p.getDestination());
             faceWay = FaceWay.Up;
             if (this.isWalkable(0, 1)) faceWay = FaceWay.Right;
             else if (this.isWalkable(0, -1)) faceWay = FaceWay.Left;
             else if (this.isWalkable(1, 0)) faceWay = FaceWay.Down;
             return true;
        }
        if (getY() < y) faceWay = FaceWay.Down;
        else if (getY() > y) faceWay = FaceWay.Up;
        else if (getX() < x) faceWay = FaceWay.Right;
        else faceWay = FaceWay.Left;
        setY(y);
        setX(x);
        return true;
    }

    public int getCostOfWalkOne(int yDiff, int xDiff) {
        if (!isWalkable(yDiff, xDiff)) return Integer.MAX_VALUE;
        FaceWay newFace;
        int y = getY() + yDiff;
        int x = getX() + xDiff;

        if (getY() < y) newFace = FaceWay.Down;
        else if (getY() > y) newFace = FaceWay.Up;
        else if (getX() < x) newFace = FaceWay.Right;
        else newFace = FaceWay.Left;

        int res = 1;
        if (newFace != faceWay) res += this.faceWay.getDistance(newFace) * FaceWay.turningConst;
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Location loc)) return false;
        return (super.equals(loc) && ground.equals(loc.ground) && faceWay.equals(loc.faceWay));
    }

}
