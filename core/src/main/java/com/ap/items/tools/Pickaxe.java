package com.ap.items.tools;

import com.ap.model.Abilities;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Pickaxe extends Tool {
    public Pickaxe(TextureRegion icon) {
        super("Pickaxe", icon, Abilities.Mining);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }
}
