package phi.ap.model;

public class EnergyManager {
    public static final int unit = 100;
    public static final int turningCost = 10 * unit / 20;
    public static final int walkCost = unit / 20;
    private int maxAmount;
    private int amount;
    public EnergyManager() {
        maxAmount = 200 * unit;
        amount = maxAmount;
    }
    public void advanceBaseUnit(int diff) { // amount += diff * unit;
        amount += diff * unit;
        amount = Math.min(amount, maxAmount);
        amount = Math.max(0, amount);
    }

    public void advanceBaseInt(int diff) {
        amount += diff;
        amount = Math.min(amount, maxAmount);
        amount = Math.max(0, amount);
    }

    public int getMaxAmount() {
        return amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void reduceEnergy(int amount){
        this.amount -= amount * unit;
        //feint
        //TODO
    }
    public boolean hasEnergy(int amount){
        return this.amount >= amount*unit;
    }
}
