package phi.ap.service;

import phi.ap.model.*;
import phi.ap.model.enums.FaceWay;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class MapService {
    private Map map;

    public Map getMap() {
        return map;
    }

    public MapService(Map map) {
        this.map = map;
    }

    public void addDoor(Ground father, Ground child, Tile doorShape) {
        child.setTile(doorShape.getCoordinate().getY(), doorShape.getCoordinate().getX(),
                doorShape.getTile());
        Portal.makePortalTwoWay(father, father.getCoordinateOfCoordinateInChild(child, doorShape.getCoordinate()),
                child, doorShape.getCoordinate());
    }

    public int getDistance(Coordinate source, Coordinate sink) {
        int res = Math.abs(source.getY() - sink.getY()) + Math.abs(source.getX() - sink.getX());
        if (source.getY() != sink.getY() && source.getX() != sink.getX()) {
            res += FaceWay.turningConst;
        }
        return res;
    }

    public int getHeuristicTravelCost(Coordinate source, Coordinate sink) {
        if (!map.isCoordinateValid(source.getY(), source.getX()) ||
                !map.isCoordinateValid(sink.getY(), sink.getX())) {
            return Integer.MAX_VALUE;
        }
        return getDistance(source, sink);
    }

    public Path getPath(Location source, Location sink) {
        class Node {
            public Location location;
            public Node parent;
            public Coordinate step;
            public boolean isSource;
            public int costFromSource;
            public final int heuristicCostToSink;
            public Node(Location location, Node parent, Coordinate step) {
                this.location = location;
                this.parent = parent;
                isSource = (parent == null);
                heuristicCostToSink = getHeuristicTravelCost(location, sink);
                if (isSource) costFromSource = 0;
                else costFromSource = Integer.MAX_VALUE;
                this.step = step;
            }

            public Node(Location location, Node parent, int costFromSource, Coordinate step) {
                this.location = location;
                this.parent = parent;
                this.costFromSource = costFromSource;
                isSource = (parent == null);
                heuristicCostToSink = getHeuristicTravelCost(location, sink);
                this.step = step;
            }

            public Path getPath() {
                if (isSource) {
                    Path path = new Path();
                    path.setSource(location);
                    return path;
                }
                Path path = parent.getPath();
                path.addStep(step);
                return path;
            }

            public Location getLocation() {
                return location;
            }

            public Node getParent() {
                return parent;
            }

            public boolean isSource() {
                return isSource;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public void setParent(Node parent) {
                this.parent = parent;
            }

            public void setSource(boolean source) {
                isSource = source;
            }

            public int totalCost() {
                return costFromSource + heuristicCostToSink;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null) return false;
                if (!(obj instanceof Node)) return false;
                Node n = (Node) obj;
                return location.getX() == n.getLocation().getX() && location.getY() == n.getLocation().getY();
            }

            public void setCostFromSource(int costFromSource) {
                this.costFromSource = costFromSource;
            }
        }
        if (!map.isCoordinateValid(source.getY(), source.getX()) ||
            !map.isCoordinateValid(sink.getY(), sink.getX())) {
            return null;
        }

        PriorityQueue<Node> sack = new PriorityQueue<>(Comparator.comparing(Node::totalCost)
                .thenComparing(e -> e.costFromSource));
//        HashMap<Coordinate, Integer> g = new HashMap<>();
        Node src;
        sack.add(src = new Node(source, null, 0, null));
//        g.put(source, src.totalCost());
        Node dst = null;
        while (!sack.isEmpty()) {
            Node n = sack.poll();
//            if (g.get(n.getLocation()) < n.totalCost()) continue;
            if (n.getLocation().getCoordinate().equals(sink.getCoordinate())) {
                dst = n;
                break;
            }
            int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            Ground ground = n.getLocation().getGround();
            for (int[] d : dir) {
                int y = n.getLocation().getY() + d[0];
                int x = n.getLocation().getX() + d[1];
                if (!n.getLocation().isWalkable(d[0], d[1])) continue;
                int price = n.costFromSource + n.getLocation().getCostOfWalkOne(d[0], d[1]);
                Ground newGround = ground;
                if (ground.getPortal(y, x) != null) {
                    Portal p = ground.getPortal(y, x);
                    newGround = p.getDestination();
                    y = p.getCoordinateOnDest().getY();
                    x = p.getCoordinateOnDest().getX();

                }
                Location uL = new Location(new Coordinate(y, x), newGround,
                        n.getLocation().getFaceWayOfWalk(d[0], d[1]));
                Node u = new Node(uL, n, price, new Coordinate(d[0], d[1]));
                sack.add(u);
            }
        }
        if (dst == null) return null;
        Path path = dst.getPath();
        path.setCost(dst.totalCost());
        path.setDone(true);
        path.setTarget(dst.getLocation());
        return path;
    }

    public Location getLocationOnMap(int y, int x) {
        return map.getLocation(new Coordinate(y, x));
    }
}
