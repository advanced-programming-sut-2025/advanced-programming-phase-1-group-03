package phi.ap.service;

import phi.ap.model.*;
import phi.ap.model.enums.FaceWay;

import java.util.Comparator;
import java.util.PriorityQueue;

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

/*    public Path getPath(Location source, Location sink) {
        class Node {
            public Location location;
            public Node parent;
            public boolean isSource;
            public int costFromSource;
            public final int heuristicCostToSink;
            public Node(Location location, Node parent) {
                this.location = location;
                this.parent = parent;
                isSource = (parent == null);
                heuristicCostToSink = getHeuristicTravelCost(location, sink);
                if (isSource) costFromSource = 0;
                else costFromSource = Integer.MAX_VALUE;
            }

            public Node(Location location, Node parent, int costFromSource) {
                this.location = location;
                this.parent = parent;
                this.costFromSource = costFromSource;
                isSource = (parent == null);
                heuristicCostToSink = getHeuristicTravelCost(location, sink);
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
        Path path = new Path(source, sink);
        PriorityQueue<Node> sack = new PriorityQueue<>(Comparator.comparing(Node::totalCost)
                .thenComparing(e -> e.costFromSource));
        sack.add(new Node(source, null, 0));
        while (!sack.isEmpty()) {
            Node n = sack.poll();
            int[][] dir = {{1, 0}, {-1. }}
        }
    }

 */
}
