package com.ap.items.tools;

import com.ap.asset.AtlasAsset;
import com.ap.managers.PlayerManager;
import com.ap.model.AbilityType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class FishingPole extends Tool{
    private FishingPoleLevels currentLevel = FishingPoleLevels.Training;

    public FishingPole(AtlasAsset atlasAsset, String atlasKey) {
        super("FishingPole", atlasAsset, atlasKey, AbilityType.Fishing);
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
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        super.applyItem(body, engine, playerManager, world);
    }

    public enum FishingPoleLevels {
        Training,
        Bamboo,
        FiberGlass,
        Iridium
    }
}
