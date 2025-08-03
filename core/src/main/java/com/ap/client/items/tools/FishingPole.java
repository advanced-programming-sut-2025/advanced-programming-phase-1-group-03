package com.ap.client.items.tools;

import com.ap.client.model.AbilityType;
import com.ap.client.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class FishingPole extends Tool{
    private FishingPoleLevels currentLevel = FishingPoleLevels.Training;

    public FishingPole(TextureRegion icon) {
        super("FishingPole", icon, AbilityType.Fishing);
    }

    @Override
    public int getEnergyConsumption() {
        return switch(currentLevel) {
            case Training -> 8;
            case Bamboo -> 8;
            case FiberGlass -> 6;
            case Iridium -> 4;
        };
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        super.applyItem(body, engine, game, world);
    }

    public enum FishingPoleLevels {
        Training,
        Bamboo,
        FiberGlass,
        Iridium
    }
}
