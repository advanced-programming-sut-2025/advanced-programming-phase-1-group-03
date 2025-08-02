package com.ap.system.universal;

public class EnergyManager {
    private int amount;
    private int maxAmount;

    private static EnergyManager instance;

    public static EnergyManager getInstance() {
        if (instance == null)
            instance = new EnergyManager();
        return instance;
    }

    private EnergyManager() {
        maxAmount = 500;
        this.amount = maxAmount;
    }

    public void advance(int diff) {
        if(diff > 0)
            amount = Math.min(maxAmount, amount + diff);
        else
            amount = Math.max(0, amount - diff);
        if(amount <= 0)
            faint();
    }

    private void faint() {
        //TODO implement fainting here
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMaxAmount(int amount) {
        this.maxAmount = amount;
    }

    public boolean hasEnergy(int amount) {
        if(this.amount >= amount)
            return true;
        return false;
    }

    public void reset() {
        this.amount = maxAmount;
    }
}
