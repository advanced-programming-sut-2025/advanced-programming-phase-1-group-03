package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.items.EntityFactory;
import com.ap.model.Abilities;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Hoe extends Tool{
    public Hoe(TextureRegion icon) {
        super("Hoe", icon, Abilities.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(Body body, Engine engine, GdxGame game, World world) {
        // It's a ground tile
        if(body.getUserData() instanceof TiledMapTile tile) {
            // It's not dirt
            if(!tile.getProperties().get("Type", "", String.class).equals("Dirt")) {
                return;
            }
            Entity entity = EntityFactory.CreatePlowedDirt(body, world);
            game.getAudioService().playSound(SoundAsset.HoeHit, 0.2f);
            engine.addEntity(entity);
        }
    }


}
