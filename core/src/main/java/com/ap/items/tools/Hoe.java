package com.ap.items.tools;

import com.ap.asset.SoundAsset;
import com.ap.items.EntityFactory;
import com.ap.model.AbilityType;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Hoe extends Tool{
    public Hoe(TextureRegion icon) {
        super("Hoe", icon, AbilityType.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        // It's a ground tile
        if(body.getUserData() instanceof TiledMapTile tile) {
            // It's not dirt
            if(!tile.getProperties().get("Type", "", String.class).equals("Dirt")) {
                return;
            }
            Entity entity = EntityFactory.instance.CreatePlowedDirt(body.getPosition(), world);
            game.getAudioService().playSound(SoundAsset.HoeHit, 0.2f);
            engine.addEntity(entity);
        }
    }


}
