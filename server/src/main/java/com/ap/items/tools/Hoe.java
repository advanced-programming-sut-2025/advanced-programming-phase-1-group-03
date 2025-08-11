package com.ap.items.tools;

import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.items.EntityFactory;
import com.ap.managers.PlayerManager;
import com.ap.model.AbilityType;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Hoe extends Tool{
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;
    public Hoe(AtlasAsset atlasAsset, String atlasKey) {
        super("Hoe", atlasAsset, atlasKey, AbilityType.Foraging);
    }

    @Override
    public int getEnergyConsumption() {
        return switch(currentLevel) {
            case Normal -> 5;
            case Copper -> 4;
            case Iron -> 3;
            case Gold -> 2;
            case Iridium -> 1;
        };
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        super.applyItem(body, engine, playerManager, world);
        // It's a ground tile
        if(body.getUserData() instanceof TiledMapTile tile) {
            // It's not dirt
            if(!tile.getProperties().get("Type", "", String.class).equals("Dirt")) {
                return;
            }
            Entity entity = EntityFactory.instance.CreatePlowedDirt(body.getPosition(), world);
            playerManager.getAudioService().playSound(SoundAsset.HoeHit, 0.2f);
            Helper.addEntity(entity, engine);
        }
    }


}
