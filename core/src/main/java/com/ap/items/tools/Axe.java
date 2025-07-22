package com.ap.items.tools;

import com.ap.model.Abilities;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Axe extends Tool {
    public Axe(TextureRegion icon) {
        super("Axe", icon, Abilities.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }
}
