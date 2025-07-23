package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.model.Abilities;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Axe extends Tool {
    public Axe(TextureRegion icon) {
        super("Axe", icon, Abilities.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(Body body, Engine engine, GdxGame game, World world) {
    }
}
