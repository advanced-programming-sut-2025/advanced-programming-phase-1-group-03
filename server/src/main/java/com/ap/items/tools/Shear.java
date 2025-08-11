package com.ap.items.tools;

import com.ap.asset.AtlasAsset;
import com.ap.managers.PlayerManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Shear extends Tool{
    public Shear(AtlasAsset atlasAsset, String atlasKey) {
        super("Shear", atlasAsset, atlasKey, null);
    }

    @Override
    public int getEnergyConsumption() {
        return 4;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        super.applyItem(body, engine, playerManager, world);
    }
}
