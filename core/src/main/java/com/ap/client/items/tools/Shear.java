package com.ap.client.items.tools;

import com.ap.client.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Shear extends Tool{
    public Shear(TextureRegion icon) {
        super("Shear", icon, null);
    }

    @Override
    public int getEnergyConsumption() {
        return 4;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        super.applyItem(body, engine, game, world);
    }
}
