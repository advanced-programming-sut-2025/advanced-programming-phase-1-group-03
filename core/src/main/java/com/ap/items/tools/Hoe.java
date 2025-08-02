package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.items.EntityFactory;
import com.ap.model.Abilities;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Hoe extends Tool{
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;
    public Hoe(TextureRegion icon) {
        super("Hoe", icon, Abilities.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return switch(currentLevel) {
            case Normal -> 5;
            case Copper -> 4;
            case Iron -> 3;
            case Gold -> 2;
            case Iridium -> 1;
        };
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
