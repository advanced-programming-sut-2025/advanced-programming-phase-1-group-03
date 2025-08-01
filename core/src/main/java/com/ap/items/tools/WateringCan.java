package com.ap.items.tools;

import com.ap.asset.SoundAsset;
import com.ap.component.Growable;
import com.ap.managers.AbilityManager;
import com.ap.model.AbilityType;
import com.ap.model.Weather;
import com.ap.screen.GameScreen;
import com.ap.system.universal.EnergyManager;
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

    public WateringCan(TextureRegion icon)
    {
        super("WateringCan", icon, AbilityType.Farming);
        capacities = new ArrayList<>(List.of(4, 6, 8, 12, 16));
    }

    @Override
    int getEnergyConsumption(GameScreen gameScreen, boolean successful) {
        int amount = 0;
        switch(currentLevel) {
            case Normal : {
                amount = 5;
                break;
            }
            case Copper : {
                amount = 4;
                break;
            }
            case Iron : {
                amount = 3;
                break;
            }
            case Gold : {
                amount = 2;
                break;
            }
            case Iridium : {
                amount = 1;
                break;
            }
        }
        if(AbilityManager.getInstance().getAbility(AbilityType.Farming).getLevel() == relatedAbility.maxLevel)
            amount -= 1;
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Rain))
            amount = (int)(amount * 1.5f);
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Snow))
            amount *= 2;
        return Math.min(0, -amount);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
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
