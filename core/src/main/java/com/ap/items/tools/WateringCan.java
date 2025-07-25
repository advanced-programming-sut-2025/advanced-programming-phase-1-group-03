package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.model.Abilities;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class WateringCan extends Tool{
    public WateringCan(TextureRegion icon) {
        super("WateringCan", icon, null);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(Body body, Engine engine, GdxGame game, World world) {
        super.applyItem(body, engine, game, world);
    }
}
