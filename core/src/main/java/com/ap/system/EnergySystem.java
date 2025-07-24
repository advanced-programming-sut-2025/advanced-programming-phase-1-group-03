package com.ap.system;

import com.ap.Constraints;
import com.ap.model.Season;
import com.ap.ui.widget.Clock;
import com.ap.ui.widget.EnergyBar;
import com.badlogic.ashley.core.EntitySystem;

public class EnergySystem extends EntitySystem {

    private final EnergyBar energyBar;


    public EnergySystem(EnergyBar energyBar) {
        this.energyBar = energyBar;
    }

    @Override
    public void update(float deltaTime) {
        energyBar.setEnergyPercent(energyBar.getEnergyPercent() - 0.001f);
    }
}