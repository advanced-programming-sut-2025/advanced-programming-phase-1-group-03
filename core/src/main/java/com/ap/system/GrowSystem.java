package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.component.Graphic;
import com.ap.component.Growable;
import com.ap.component.ItemHolder;
import com.ap.items.Item;
import com.ap.items.plant.Crop;
import com.ap.model.Weather;
import com.ap.ui.widget.ItemContainer;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class GrowSystem extends IteratingSystem implements EntityListener {
    private AssetService assetService;
    private WeatherSystem weatherSystem;
    public GrowSystem(AssetService assetService, WeatherSystem weatherSystem) {
        super(Family.all(Growable.class).get());
        this.assetService = assetService;
        this.weatherSystem = weatherSystem;
    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(getFamily(), this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Growable growable = Growable.mapper.get(entity);
        Item item = ItemHolder.mapper.get(entity).getItem();

        int elapsedDay = growable.getElapsedDay();


        // If crop is regrowing it doesn't follow stages
        if(item instanceof Crop crop && crop.isRegrowing()) {
            if(!growable.canProduce() && elapsedDay >= crop.getType().getRegrowthTime()) {
                elapsedDay = 0;
                growable.setCurrentStage(growable.getCurrentStage() + 1);
            }
        } else {
            int currentStage = growable.getCurrentStage();
            if (currentStage < growable.getStages().size() && elapsedDay >= growable.getStages().get(currentStage)) {
                growable.setCurrentStage(currentStage + 1);
                growable.setElapsedDay(0);
            }
        }

        if(Graphic.mapper.has(entity)) {
            Graphic graphic = Graphic.mapper.get(entity);
            var texture = assetService.get(growable.getAtlas()).
                    findRegions(growable.getAssetKey() + "_Stage").get(growable.getCurrentStage());
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

    @Override
    public void entityAdded(Entity entity) {
        if(weatherSystem.getCurrentWeather() == Weather.Rain) {
            Growable growable = Growable.mapper.get(entity);
            growable.setWateredToday(true);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
