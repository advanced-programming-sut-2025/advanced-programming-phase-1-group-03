package com.ap.items.tools;

import com.ap.managers.AbilityManager;
import com.ap.model.AbilityType;
import com.ap.model.Weather;
import com.ap.screen.GameScreen;
import com.ap.system.universal.EnergyManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class FishingPole extends Tool{
    private FishingPoleLevels currentLevel = FishingPoleLevels.Training;

    public FishingPole(TextureRegion icon) {
        super("FishingPole", icon, AbilityType.Fishing);
    }

    @Override
    int getEnergyConsumption(GameScreen gameScreen, boolean successful) {
        int amount = 0;
        switch(currentLevel) {
            case Training, Bamboo: {
                amount = 8;
                break;
            }
            case FiberGlass: {
                amount = 6;
                break;
            }
            case Iridium: {
                amount = 4;
                break;
            }
        }
        if(AbilityManager.getInstance().getAbility(AbilityType.Fishing).getLevel() == relatedAbility.maxLevel)
            amount -= 1;
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Rain))
            amount = (int)(amount * 1.5f);
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Snow))
            amount *= 2;
        return Math.min(0, -amount);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        super.applyItem(body, engine, game, world);
        EnergyManager.getInstance().advance(getEnergyConsumption(game, true));
    }

    public enum FishingPoleLevels {
        Training,
        Bamboo,
        FiberGlass,
        Iridium
    }
}
