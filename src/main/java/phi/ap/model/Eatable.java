package phi.ap.model;

public class Eatable {

    //TODO buff
    private int energy;
    public Eatable(int energy) {
        this.energy = energy;
    }

    public Eatable(Eatable eatable) {
        this.energy = eatable.getEnergy();
    }

    public int getEnergy() {
        return energy;
    }
}
