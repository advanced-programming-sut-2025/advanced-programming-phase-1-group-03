package com.ap.system;

import com.ap.ui.widget.EnergyBar;
import com.badlogic.ashley.core.EntitySystem;

public class EnergySystem extends EntitySystem {

    private final EnergyBar energyBar;
    public EnergySystem(EnergyBar energyBar) {
        this.energyBar = energyBar;
    }

    @Override
    public void update(float deltaTime) {

        //energyBar.setEnergyPercent(energyManager.getPercentage());
    }
}