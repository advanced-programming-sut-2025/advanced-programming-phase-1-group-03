package com.ap.system;

import com.ap.managers.EnergyManager;
import com.ap.ui.widget.EnergyBar;
import com.badlogic.ashley.core.EntitySystem;

public class EnergySystem extends EntitySystem {

    private final EnergyBar energyBar;
    private EnergyManager energyManager;
    public EnergySystem(EnergyBar energyBar, EnergyManager energyManager) {
        this.energyBar = energyBar;
        this.energyManager = energyManager;
    }

    @Override
    public void update(float deltaTime) {
        energyBar.setEnergyPercent(energyManager.getPercentage());
    }
}