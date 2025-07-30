package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.component.Growable;
import com.ap.model.Abilities;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class WateringCan extends Tool{
    private final ArrayList<Integer> capacities;
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;
    int currentOccupied = 0;

    public WateringCan(TextureRegion icon)
    {
        super("WateringCan", icon, null);
        capacities = new ArrayList<>(List.of(4, 6, 8, 12, 16));
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(Body body, Engine engine, GameScreen game, World world) {
        if(body.getUserData() instanceof TiledMapTile tile) {
            String water = tile.getProperties().get("Water", "", String.class);
            if(water.equals("T")) {
                game.getAudioService().playSound(SoundAsset.Watering);
                currentOccupied = capacities.get(currentLevel.ordinal());
            }
        }
        if(currentOccupied == 0) {
            game.getAudioService().playSound(SoundAsset.WateringCanNo);
            return;
        }
        currentOccupied --;
        game.getAudioService().playSound(SoundAsset.Watering);
        if(body.getUserData() instanceof Entity entity && Growable.mapper.has(entity)) {
            Growable growable = Growable.mapper.get(entity);
            growable.setWateredToday(true);
        }
    }
}
