package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.component.Graphic;
import com.ap.component.Growable;
import com.ap.model.Weather;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class GrowSystem extends IteratingSystem {
    private AssetService assetService;
    private WeatherSystem weatherSystem;
    public GrowSystem(AssetService assetService, WeatherSystem weatherSystem) {
        super(Family.all(Growable.class).get());
        this.assetService = assetService;
        this.weatherSystem = weatherSystem;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Growable growable = Growable.mapper.get(entity);
        int elapsedDay = growable.getElapsedDay();

        int currentStage = growable.getStages().size();
        for(int i = 0; i < growable.getStages().size(); ++i) {
            int stage = growable.getStages().get(i);
            elapsedDay -= stage;
            if(elapsedDay < 0) {
                currentStage = i + 1;
                break;
            }
        }

        if(Graphic.mapper.has(entity)) {
            Graphic graphic = Graphic.mapper.get(entity);
            var texture = assetService.get(growable.getAtlas()).findRegions(growable.getAssetKey() + "_Stage").get(currentStage);
            graphic.setTexture(texture);
        }
    }

    public void dayPassed() {
        for(Entity entity : getEntities()) {
            Growable growable = Growable.mapper.get(entity);
            if(growable.isWateredToday()) {
                growable.setElapsedDay(growable.getElapsedDay() + 1);
            }

            if(weatherSystem.getCurrentWeather().equals(Weather.Rain)) {
                growable.setWateredToday(true);
            }else {
                growable.setWateredToday(false);
            }
        }
    }
}
