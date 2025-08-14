package com.ap.managers;

import com.ap.Constraints;
import com.ap.items.tools.Tool;
import com.ap.model.ServerPlayer;
import com.ap.model.Weather;
import com.ap.notifiers.EnergyNotifier;
import com.ap.system.universal.WeatherSystem;

public class EnergyManager {
    private WeatherSystem weatherSystem;
    private AbilityManager abilityManager;
    private int amount;
    private final int maxAmount = Constraints.MAX_ENERGY_AMOUNT;
    private final ServerPlayer player;

    public float getPercentage() {
        return ((float)amount/(float)maxAmount);
    }

    public void setPercentage(float percentage) {
        setAmount(Math.round(percentage*maxAmount / 100f));
    }
    public int getConsumedEnergy() {
        return maxAmount - amount;
    }

    public EnergyManager(WeatherSystem weatherSystem, AbilityManager abilityManager, ServerPlayer player) {
        this.amount = maxAmount;
        this.abilityManager = abilityManager;
        this.weatherSystem = weatherSystem;
        this.player = player;
    }

    public void advance(int diff) {
        if(diff > 0)
            amount = Math.min(maxAmount, amount + diff);
        else
            amount = Math.max(0, amount + diff);
        if(amount <= 0)
            faint();
        player.connection.sendTCP(new EnergyNotifier(getPercentage()));
    }

    private void faint() {
        //TODO implement fainting here
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        player.connection.sendTCP(new EnergyNotifier(getPercentage()));
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
