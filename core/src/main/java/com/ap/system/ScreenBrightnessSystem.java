package com.ap.system;

import box2dLight.RayHandler;
import com.ap.component.Graphic;
import com.ap.component.Shadow;
import com.ap.model.Weather;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;

import static com.ap.system.TimeSystem.*;

public class ScreenBrightnessSystem extends EntitySystem {
    private final Engine engine;
    private final RayHandler rayHandler;
    private final TimeSystem timeSystem;
    private final WeatherSystem weatherSystem;

    public ScreenBrightnessSystem(RayHandler rayHandler, Engine engine) {
        this.engine = engine;
        this.rayHandler = rayHandler;
        timeSystem = engine.getSystem(TimeSystem.class);
        weatherSystem = engine.getSystem(WeatherSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        float darknessProgress = MathUtils.clamp(timeSystem.totalSecondsFrom(timeSystem.getTotalSeconds(), darknessBegin)
                / ((darknessEnd - darknessBegin) * 60 * 60f), 0f, 1f);

        float darkness = 1 - darknessProgress / 2f;

        if(weatherSystem.getCurrentWeather() == Weather.Sunny) {
        }else if(weatherSystem.getCurrentWeather() == Weather.Rain) {
            darkness = Math.min(1 - darknessProgress / 2f, 0.7f);
        } else {
            darkness = 0.8f;
        }

        rayHandler.setAmbientLight(darkness, darkness, darkness, 1f);

        for(Entity shadow : engine.getEntitiesFor(Family.all(Shadow.class).get())) {
            Graphic graphic = Graphic.mapper.get(shadow);
            graphic.getColor().a = 1 - darknessProgress;
        }
    }
}
