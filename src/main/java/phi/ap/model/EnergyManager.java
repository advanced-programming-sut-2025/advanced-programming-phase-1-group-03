package phi.ap.model;

public class EnergyManager {
    public static final int unit = 100;
    public static final int turningCost = 10 * unit / 20;
    public static final int walkCost = unit / 20;
    private final Player player;
    private int maxAmount;
    private int amount;
    public EnergyManager(Player player) {
        maxAmount = 200 * unit;
        amount = maxAmount;
        this.player = player;
    }
    public void advanceBaseUnit(int diff) { // amount += diff * unit;
        amount = Math.min(amount, getMaxAmount());
        amount += diff * unit;
        amount = Math.min(amount, getMaxAmount());
        amount = Math.max(0, amount);
        if (amount == 0) Game.getInstance().getCurrentPlayer().feint();
    }

    public void advanceBaseInt(int diff) {
        amount += diff;
        amount = Math.min(amount, getMaxAmount());
        amount = Math.max(0, amount);
        if (amount == 0) Game.getInstance().getCurrentPlayer().feint();
    }

    public int getMaxAmount() {
        if (player.getLastFoodBuff() != null && player.getLastFoodBuff().isActive() && player.getLastFoodBuff().getMaxEnergyEffect() != null) {
            return Math.min(maxAmount + player.getLastFoodBuff().getMaxEnergyEffect(), Integer.MAX_VALUE);
        }
        if (player.getFeintBuff().isActive()) return (int) (maxAmount * player.getFeintBuff().getDailyEnergyEffect());
        return maxAmount;
    }

    public int getAmount() {
        return Math.min(amount, getMaxAmount());
    }
    public int getAmountBaseUnit() {
        return Math.ceilDiv(getAmount(), unit);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public boolean hasEnergy(int amount){
        return this.amount >= amount*unit;
    }

    public void reset() {
        amount = getMaxAmount();
    }
}
