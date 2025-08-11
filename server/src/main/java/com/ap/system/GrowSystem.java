package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.component.Graphic;
import com.ap.component.Growable;
import com.ap.component.ItemHolder;
import com.ap.items.Item;
import com.ap.items.plant.Crop;
import com.ap.model.Weather;
import com.ap.system.universal.WeatherSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;

public class GrowSystem extends IteratingSystem implements EntityListener {
    private final WeatherSystem weatherSystem;
    public GrowSystem(WeatherSystem weatherSystem) {
        super(Family.all(Growable.class).get());
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

        if(growable.getDirtEntity() != null) {
            Graphic graphic = Graphic.mapper.get(growable.getDirtEntity());
            if(growable.isWateredToday()) {
                graphic.setColor(new Color(0.5f, 0.5f, 0.5f, 1));
            } else {
                graphic.setColor(new Color(1f, 1f, 1f, 1f));
            }
        }
        if(Graphic.mapper.has(entity)) {
            Graphic graphic = Graphic.mapper.get(entity);
            graphic.setAtlasKey(growable.getAssetKey() + "_Stage");
            graphic.setAtlas(growable.getAtlas());
            graphic.setRegionIndex(growable.getCurrentStage());
        }
    }

    public void dayPassed() {
        for(Entity entity : getEntities()) {
            Growable growable = Growable.mapper.get(entity);
            if(growable.isWateredToday()) {
                growable.setElapsedDay(growable.getElapsedDay() + 1);
            }

            growable.setWateredToday(weatherSystem.getCurrentWeather().equals(Weather.Rain));
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
