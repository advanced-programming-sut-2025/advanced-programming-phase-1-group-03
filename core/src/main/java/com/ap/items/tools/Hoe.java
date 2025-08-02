package com.ap.items.tools;

import com.ap.asset.SoundAsset;
import com.ap.items.EntityFactory;
import com.ap.model.AbilityType;
import com.ap.model.Weather;
import com.ap.screen.GameScreen;
import com.ap.system.universal.EnergyManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Hoe extends Tool{
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;
    public Hoe(TextureRegion icon) {
        super("Hoe", icon, AbilityType.Foraging);
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
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Rain))
            amount = (int)(amount * 1.5f);
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Snow))
            amount *= 2;
        return Math.min(0, -amount);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        // It's a ground tile
        if(body.getUserData() instanceof TiledMapTile tile) {
            // It's not dirt
            if(!tile.getProperties().get("Type", "", String.class).equals("Dirt")) {
                EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
                return;
            }
            Entity entity = EntityFactory.instance.CreatePlowedDirt(body.getPosition(), world);
            game.getAudioService().playSound(SoundAsset.HoeHit, 0.2f);
            engine.addEntity(entity);
            EnergyManager.getInstance().advance(getEnergyConsumption(game, true));
        } else {
            EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
        }
    }


}
