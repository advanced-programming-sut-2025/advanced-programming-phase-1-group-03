package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.model.Abilities;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class MilkPail extends Tool{
    public MilkPail(TextureRegion icon) {
        super("MailPale",icon, null);
    }

    @Override
    int getEnergyConsumption() {
        return 4;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        super.applyItem(body, engine, game, world);
    }
}
