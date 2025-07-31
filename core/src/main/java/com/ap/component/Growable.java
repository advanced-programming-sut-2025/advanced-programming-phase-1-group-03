package com.ap.component;

import com.ap.asset.AtlasAsset;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

public class Growable implements Component {
    public final static  ComponentMapper<Growable> mapper = ComponentMapper.getFor(Growable.class);

    private ArrayList<Integer> stages;
    private AtlasAsset atlas;
    private String assetKey;
    private int elapsedDay;
    private boolean isWateredToday = false;
    private Entity dirtEntity;

    // 1 base
    private int currentStage;

    public Growable(ArrayList<Integer> stages, AtlasAsset atlas, String assetKey, Entity dirtEntity) {
        this.stages = stages;
        this.atlas = atlas;
        this.assetKey = assetKey;
        this.elapsedDay = 0;
        this.dirtEntity = dirtEntity;
    }

    public boolean canProduce() {
        return currentStage == stages.size();
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Integer> stages) {
        this.stages = stages;
    }

    public AtlasAsset getAtlas() {
        return atlas;
    }

    public void setAtlas(AtlasAsset atlas) {
        this.atlas = atlas;
    }

    public String getAssetKey() {
        return assetKey;
    }

    public void setAssetKey(String assetKey) {
        this.assetKey = assetKey;
    }

    public int getElapsedDay() {
        return elapsedDay;
    }

    public void setElapsedDay(int elapsedDay) {
        this.elapsedDay = elapsedDay;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

    public void setWateredToday(boolean wateredToday) {
        isWateredToday = wateredToday;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public Entity getDirtEntity() {
        return dirtEntity;
    }

    public void setDirtEntity(Entity dirtEntity) {
        this.dirtEntity = dirtEntity;
    }
}
