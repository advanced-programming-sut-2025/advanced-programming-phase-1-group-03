package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.model.Abilities;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class MilkPail extends Tool{
    public MilkPail(TextureRegion icon) {
        super("MailPale",icon, Abilities.None);
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
