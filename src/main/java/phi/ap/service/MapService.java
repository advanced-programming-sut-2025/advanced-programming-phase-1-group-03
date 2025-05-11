package phi.ap.service;

import phi.ap.model.*;
import phi.ap.model.Map;
import phi.ap.model.enums.FaceWay;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.Building;
import phi.ap.model.items.buildings.Farm;

import java.util.*;

public class MapService {
    private Map map;

    public Map getMap() {
        return map;
    }

    public MapService(Map map) {
        this.map = map;
    }

    public boolean checkIsPlayerHere(Ground ground, Coordinate coordinate, Player player) { // if player = null, it will check for All players
        ArrayList<Player> players = new ArrayList<>();
        if (player == null) {
            for (Player player1 : Game.getInstance().getPlayers()) {
                players.add(player1);
            }
        } else players.add(player);
        for (Player player1 : players) {
            if (player1.getLocation().getGround() == ground && player1.getLocation().getCoordinate().equals(coordinate)) {
                return true;
            }
        }
        return false;
    }

    public int getDistance(Coordinate source, Coordinate sink) {
        int res = Math.abs(source.getY() - sink.getY()) + Math.abs(source.getX() - sink.getX()) * EnergyManager.walkCost;
        if (source.getY() != sink.getY() && source.getX() != sink.getX()) {
            res += EnergyManager.walkCost;
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
        ArrayList<Location> sinks = new ArrayList<>();
        if (sink.getFaceWay() != null) sinks.add(sink);
        else {
            for (int i = 0; i < FaceWay.values().length; i++) {
                sink.setFaceWay(FaceWay.values()[i]);
                sinks.add(new Location(sink));
            }
            sink.setFaceWay(null);
        }
        class Node {
            private Location location;
            private Node parent;
            private Coordinate step;

            private int g;
            private int h;
            public Node(Location location) {
                this.location = location;
//                h = getHeuristicTravelCost(location, sink);
                h = 0;
            }

            public void setH(int h) {
                this.h = h;
            }

            public Path getPath() {
                if (parent == null) {
                    Path path = new Path();
                    path.setSource(location);
                    return path;
                }
                Path path = parent.getPath();
                path.addStep(step);
                path.setTarget(location);
                return path;
            }

            public Location getLocation() {
                return location;
            }

            public Coordinate getStep() {
                return step;
            }

            public int getG() {
                return g;
            }

            public int getH() {
                return h;
            }

            public Node getParent() {
                return parent;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public void setParent(Node parent, Coordinate step) {
                this.parent = parent;
                this.step = step;
            }

            public int f() {
                return g + h;
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Node)) {return false;}
                Node node = (Node) obj;
                return (location.getGround().equals(node.getLocation().getGround())
                    && location.getCoordinate().equals(node.getLocation().getCoordinate())
                    && location.getFaceWay().equals(node.getLocation().getFaceWay()));
            }

            @Override
            public int hashCode() {
                return Objects.hash(location);
            }

            public void setG(int g) {
                this.g = g;
            }

            public void addNeighborsToSack(PriorityQueue<Node> sack, HashMap<Node, Integer> bestG) {
                Node v = this;
                int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for (int[] d : dir) {
                    Location ul = new Location(v.getLocation());
                    int cost = ul.getCostOfWalkOne(d[0], d[1]);
                    if (!ul.walkOne(d[0], d[1])) continue;
                    Node u = new Node(ul);
                    u.setG(v.getG() + cost);
                    int betterG = bestG.getOrDefault(u, Integer.MAX_VALUE);
                    if (betterG <= u.getG()) continue;
                    u.setParent(v, new Coordinate(d[0], d[1]));
                    bestG.put(u, u.getG());
                    sack.add(u);
                }
            }
        }
        if (!source.getGround().isCoordinateValid(source.getY(), source.getX()) ||
            !source.getGround().isCoordinateValid(sink.getY(), sink.getX())) {
            return null;
        }
        Portal p;
        if ((p = sink.getGround().getPortal(sink.getY(), sink.getX())) != null) {
            Location lp = new Location(p.getCoordinateOnDest(), p.getDestination(), null);
            for (FaceWay faceWay : FaceWay.values()) {
                lp.setFaceWay(faceWay);
                sinks.add(new Location(lp));
            }
        }

        PriorityQueue<Node> sack = new PriorityQueue<>(Comparator.comparing(Node::f)
                .thenComparing(e -> e.g));
        HashMap<Node, Integer> bestG = new HashMap<>();
        Node src = new Node(source);
        src.setG(0);
        bestG.put(src, src.getG());
        sack.add(src);
        Node dest = null;
        while (!sack.isEmpty()) {
            Node v = sack.poll();
            for (Location location : sinks) {
                if (v.location.equals(location)) {
                    dest = v;
                    v = null;
                    break;
                }
            }
            if (v == null) break;
            v.addNeighborsToSack(sack, bestG);
        }
        if (dest == null) {
            return null;
        }
        Path path = dest.getPath();
        path.setCost(dest.getG());
        path.setDone(true);
        return path;
    }


    public Location getLocationOnMap(int y, int x) {

        Location loc = map.getLocation(new Coordinate(y, x));
        if (loc == null) return null;
        while (!(loc.getGround() instanceof Building) && !(loc.getGround() instanceof Map)) {
            loc.setY(loc.getY() + loc.getGround().getCoordinate().getY());
            loc.setX(loc.getX() + loc.getGround().getCoordinate().getX());
            loc.setGround(loc.getGround().getFather());
        }
        return loc;
    }

    public boolean isNeighbor(int yDiff, int xDiff) {
        return !((yDiff != 0 && xDiff != 0) || (yDiff == 0 && xDiff == 0));
    }
}
