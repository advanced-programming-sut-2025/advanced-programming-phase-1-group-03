package com.ap.client.managers;

import com.ap.client.Constraints;
import com.ap.client.items.tools.Tool;
import com.ap.client.model.Weather;
import com.ap.client.system.WeatherSystem;

public class EnergyManager {
    private WeatherSystem weatherSystem;
    private AbilityManager abilityManager;
    private int amount;
    private final int maxAmount = Constraints.MAX_ENERGY_AMOUNT;

    public float getPercentage() {
        return ((float)amount/(float)maxAmount);
    }

    public void setPercentage(float percentage) {
        setAmount(Math.round(percentage*maxAmount / 100f));
    }
    public int getConsumedEnergy() {
        return maxAmount - amount;
    }

    public EnergyManager(WeatherSystem weatherSystem, AbilityManager abilityManager) {
        this.amount = maxAmount;
        this.abilityManager = abilityManager;
        this.weatherSystem = weatherSystem;
    }

    public void advance(int diff) {
        if(diff > 0)
            amount = Math.min(maxAmount, amount + diff);
        else
            amount = Math.max(0, amount + diff);
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


    public boolean hasEnergy(int amount) {
        return this.amount >= amount;
    }

    public void reset() {
        this.amount = maxAmount;
    }

    public void reduceByUsingTool(Tool tool) {
        int base = tool.getEnergyConsumption();
        if (tool.getRelatedAbility() != null && abilityManager.getAbility(tool.getRelatedAbility()).isMax())
            base -= 1;
        if (weatherSystem.getCurrentWeather().equals(Weather.Rain)) {
            base = (int) (base * 1.5f);
        } else if(weatherSystem.getCurrentWeather().equals(Weather.Snow)) {
            base *= 2;
        }
        advance(Math.min(0, -base));
    }
}
