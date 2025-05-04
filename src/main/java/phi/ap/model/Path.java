package phi.ap.model;

import java.util.ArrayList;

public class Path {
    int cost = 0;
    ArrayList<Coordinate> steps = new ArrayList<>();
    boolean done = false;
    Location source = null;
    Location target = null;

    public Path(int cost, ArrayList<Coordinate> steps, boolean done, Location source, Location target) {
        this.cost = cost;
        this.steps = steps;
        this.done = done;
        this.source = source;
        this.target = target;
    }

    public Path(Location source, Location target) {
        this.source = source;
        this.target = target;
    }

    public Path() {
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Coordinate> getSteps() {
        return steps;
    }

    public boolean isDone() {
        return done;
    }

    public Location getSource() {
        return source;
    }

    public Location getTarget() {
        return target;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSteps(ArrayList<Coordinate> steps) {
        this.steps = steps;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public void addStep(Coordinate step) {
        steps.add(step);
    }
    public void popStep() {
        if (!steps.isEmpty()) {
            steps.removeLast();
        }
    }
}
