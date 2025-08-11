package com.ap.items.tools;

import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.Growable;
import com.ap.component.items.Well;
import com.ap.managers.PlayerManager;
import com.ap.model.AbilityType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class WateringCan extends Tool{
    private final ArrayList<Integer> capacities;
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;
    int currentOccupied = 0;

    public WateringCan(AtlasAsset atlasAsset, String atlasKey)
    {
        super("WateringCan", atlasAsset, atlasKey, AbilityType.Farming);
        capacities = new ArrayList<>(List.of(4, 6, 8, 12, 16));
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

        if(body.getUserData() instanceof TiledMapTile tile) {
            String water = tile.getProperties().get("Water", "", String.class);
            if(water.equals("T")) {
                fillWater(playerManager);
            }
        } else if(body.getUserData() instanceof Entity entity && Well.mapper.has(entity)) {
            fillWater(playerManager);
        }
        if(currentOccupied == 0) {
            playerManager.getAudioService().playSound(SoundAsset.WateringCanNo);
            return;
        }
        currentOccupied --;
        playerManager.getAudioService().playSound(SoundAsset.Watering);
        if(body.getUserData() instanceof Entity entity && Growable.mapper.has(entity)) {
            Growable growable = Growable.mapper.get(entity);
            growable.setWateredToday(true);
        }
    }

    private void fillWater(PlayerManager playerManager) {
        playerManager.getAudioService().playSound(SoundAsset.Watering);
        currentOccupied = capacities.get(currentLevel.ordinal());
    }
}
