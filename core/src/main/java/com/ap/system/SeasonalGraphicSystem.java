package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.component.Graphic;
import com.ap.component.SeasonalGraphic;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

public class SeasonalGraphicSystem extends IteratingSystem {
    private final AssetService assetService;
    private final Engine engine;
    private final TimeSystem timeSystem;

    public SeasonalGraphicSystem(AssetService assetService, Engine engine) {
        super(Family.all(Graphic.class, SeasonalGraphic.class).get());
        this.assetService = assetService;
        this.engine = engine;
        timeSystem = engine.getSystem(TimeSystem.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Graphic graphic = Graphic.mapper.get(entity);
        SeasonalGraphic seasonalGraphic = SeasonalGraphic.mapper.get(entity);
        String textureName = seasonalGraphic.getAtlasKey() + "/" + timeSystem.getSeason().name().toLowerCase();
        var atlas = assetService.get(seasonalGraphic.getAtlas());

        var newRegion = atlas.findRegion(textureName);
        if(newRegion == null) {
            return;
        }
        graphic.setTexture(newRegion);
    }

}
