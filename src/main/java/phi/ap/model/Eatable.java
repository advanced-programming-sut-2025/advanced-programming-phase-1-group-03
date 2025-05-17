package phi.ap.model;

public class Eatable {

    private Buff buff = null;
    private int energy;
    public Eatable(int energy) {
        this.energy = energy;
    }

    public Eatable(Buff buff, int energy) {
        this.buff = new Buff(buff);
        this.energy = energy;
    }

    public Eatable(Eatable eatable) {
        this.energy = eatable.getEnergy();
        this.buff = new Buff(eatable.buff);
    }

    public int getEnergy() {
        return energy;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = new Buff(buff);
    }

    public void useBuff() {
        if (buff == null) return;
    }
}
