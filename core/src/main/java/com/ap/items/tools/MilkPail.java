package com.ap.items.tools;

import com.ap.model.Weather;
import com.ap.screen.GameScreen;
import com.ap.system.universal.EnergyManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class MilkPail extends Tool{
    public MilkPail(TextureRegion icon) {
        super("MailPale",icon, null);
    }

    @Override
    int getEnergyConsumption(GameScreen gameScreen, boolean successful) {
        int amount = 4;
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
}
