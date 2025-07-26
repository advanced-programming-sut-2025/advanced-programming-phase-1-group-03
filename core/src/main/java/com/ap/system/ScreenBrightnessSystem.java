package com.ap.system;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.component.Graphic;
import com.ap.component.Shadow;
import com.ap.model.Weather;
import com.ap.system.universal.TimeSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;


public class ScreenBrightnessSystem extends EntitySystem {
    public static final int darknessBegin = Constraints.DARKNESS_BEGIN_HOUR;
    public static final int darknessEnd = Constraints.DARKNESS_END_HOUR;
    private final Engine engine;
    private final RayHandler rayHandler;
    private final TimeSystem timeSystem;
    private final WeatherSystem weatherSystem;

    public ScreenBrightnessSystem(RayHandler rayHandler, Engine engine, TimeSystem timeSystem, WeatherSystem weatherSystem) {
        this.engine = engine;
        this.timeSystem = timeSystem;
        this.rayHandler = rayHandler;
        this.weatherSystem = weatherSystem;
    }

    @Override
    public void update(float deltaTime) {
        float darknessProgress = MathUtils.clamp(TimeSystem.totalSecondsFrom(timeSystem.getTotalSeconds(), darknessBegin)
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